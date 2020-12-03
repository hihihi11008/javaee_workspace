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
	Notice notice;//������Ÿ� ����� ��������� !
	NoticeDAO noticeDAO;
	BoardList boardList;

	public BoardContent(BoardMain boardMain) {		
		super(boardMain);
		
		//���� 
		t_author = new JTextField();
		t_title = new JTextField();
		area = new JTextArea();
		scroll = new JScrollPane(area);
		bt_list = new JButton("�������");
		bt_edit = new JButton("����");
		bt_del = new JButton("����");
		noticeDAO = new NoticeDAO();
		
		//��Ÿ��
		t_author.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-10,  25));
		t_title.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-10,  25));
		scroll.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-10,  500));
		
		//����
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
				 JOptionPane.showMessageDialog(BoardContent.this, "��������");
			 }else {
				 JOptionPane.showMessageDialog(BoardContent.this, "��������");
				 //������ �����ٻ�� ... 
				 BoardList boardList = (BoardList)boardMain.getPages(Pages.valueOf("BoardList").ordinal());
				 boardList.getList();
				 boardMain.showPage(Pages.valueOf("BoardList").ordinal());
			 }
		});
		
		bt_del.addActionListener((e)->{
			//���� �������� notice_id�� �����ؾ��� 
			boardList = new BoardList(boardMain);
			int result = noticeDAO.delete(notice.getNotice_id());
			
			if(result==0) {
				 JOptionPane.showMessageDialog(BoardContent.this, "��������");
			}else {
				JOptionPane.showMessageDialog(BoardContent.this, "��������");
				//����Ʈ ���� (�˷��ּ��� ... ��_�� ) 
				BoardList boardList = (BoardList)boardMain.getPages(Pages.valueOf("BoardList").ordinal());
				boardList.getList();
				boardMain.showPage(Pages.valueOf("BoardList").ordinal());
			}
		});
	}
	
	//������Ʈ�� ������ ä���ֱ� 
	//�̸޼��带 ȣ���ϴ� �ڴ� 1���� �����͸� vo�� ��Ƽ� ȣ���ϸ�ȴ� 
	public void setData(Notice notice) {
		this.notice = notice; //���߿� ����� �� ����ؼ� �����س��� 
		
		t_author.setText(notice.getAuthor());
		t_title.setText(notice.getTitle());
		area.setText(notice.getContent());
	}
	
}
