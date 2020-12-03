package board.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import board.model.Notice;
import board.model.NoticeDAO;

public class BoardContent extends Page{
	JTextField t_author;
	JTextField t_title;
	JTextArea area;
	JScrollPane scroll;
	JButton bt_list, bt_edit, bt_del;
	Notice notice;//써먹을거를 대비해 멤버변수로 !
	NoticeDAO noticeDAO;
	BoardList boardList;

	public BoardContent(BoardMain boardMain) {		
		super(boardMain);
		
		//생성 
		t_author = new JTextField();
		t_title = new JTextField();
		area = new JTextArea();
		scroll = new JScrollPane(area);
		bt_list = new JButton("목록으로");
		bt_edit = new JButton("수정");
		bt_del = new JButton("삭제");
		noticeDAO = new NoticeDAO();
		
		//스타일
		t_author.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-10,  25));
		t_title.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-10,  25));
		scroll.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-10,  500));
		
		//조립
		add(t_author);
		add(t_title);
		add(scroll);
		
		add(bt_list);
		add(bt_edit);
		add(bt_del);

		bt_list.addActionListener((e)->{
			boardMain.showPage(Pages.valueOf("BoardList").ordinal());
		});

		bt_edit.addActionListener((e)->{
			int notice_id = notice.getNotice_id();
			
			Notice notice = new Notice();
			notice.setAuthor(t_author.getText());
			notice.setTitle(t_title.getText());
			notice.setContent(area.getText());
			notice.setNotice_id(notice_id);
			
			 int result = noticeDAO.update(notice);
			 if(result==0) {
				 JOptionPane.showMessageDialog(BoardContent.this, "수정실패");
			 }else {
				 JOptionPane.showMessageDialog(BoardContent.this, "수정성공");
				 //갱신좀 시켜줄사람 ... 
				 BoardList boardList = (BoardList)boardMain.getPages(Pages.valueOf("BoardList").ordinal());
				 boardList.getList();
				 boardMain.showPage(Pages.valueOf("BoardList").ordinal());
			 }
		});
		
		bt_del.addActionListener((e)->{
			//현재 페이지의 notice_id를 추출해야해 
			boardList = new BoardList(boardMain);
			int result = noticeDAO.delete(notice.getNotice_id());
			
			if(result==0) {
				 JOptionPane.showMessageDialog(BoardContent.this, "삭제실패");
			}else {
				JOptionPane.showMessageDialog(BoardContent.this, "삭제성공");
				//리스트 갱신 (알려주세여 ... ㅠ_ㅠ ) 
				BoardList boardList = (BoardList)boardMain.getPages(Pages.valueOf("BoardList").ordinal());
				boardList.getList();
				boardMain.showPage(Pages.valueOf("BoardList").ordinal());
			}
		});
	}
	
	//컴포넌트에 데이터 채워넣기 
	//이메서드를 호출하는 자는 1건의 데이터를 vo에 담아서 호출하면된다 
	public void setData(Notice notice) {
		this.notice = notice; //나중에 써먹을 거 대비해서 보관해놓음 
		
		t_author.setText(notice.getAuthor());
		t_title.setText(notice.getTitle());
		area.setText(notice.getContent());
	}
	
}
