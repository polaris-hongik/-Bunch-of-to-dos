package project_todo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
 
@SuppressWarnings("serial")
public class Mainpage extends JFrame implements ActionListener{
	private JPanel area1, area2, area3, area4;
	private JPanel buttons;
	private JTable table;
	private JLabel subtitle1;
	private JButton sel_done, sel_change, sel_del;
	private JButton n_lecture, n_assign, n_sched;
	private JButton refresh, saveandquit, save;
	private JScrollPane scrollPane;
	private ButtonGroup done, type;
	private JRadioButton pos, neg, lec, ass, sch;
	private Data data;
	String[] colNames = {"����", "���� �ð�", "���� �ð�", "�Ϸ� ����", "����"};
	DefaultTableModel model;
	SimpleDateFormat date_f = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat date_time_f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
    public Mainpage(String str, Data data) throws ParseException{
        super(str);  //�������� Ÿ��Ʋ
        this.data = data;
        setSize(750,600);        //�����̳� ũ�� ����
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //�⺻ ���̾ƿ��� FlowLayout���� ����    �¿� ���� 20px, ���� ���� 50px
        setLayout(new FlowLayout(FlowLayout.LEFT,20,20));
        
      	//ǥ�� �� �����͵�.. /ó���� �� ���̺� ����� ����.. �����Ͱ�����ü ����
        Calendar target = Calendar.getInstance();
      	String todo_data[][] = data.read(target);
        model = new DefaultTableModel(todo_data,colNames);
        
      	area1 = new JPanel(new GridLayout(1,1));
      	table = new JTable(model);
      	scrollPane = new JScrollPane(table);
      	area1.add(scrollPane);
      	table.getColumn("����").setPreferredWidth(200);
      	table.getColumn("���� �ð�").setPreferredWidth(50);
      	table.getColumn("���� �ð�").setPreferredWidth(50);
      	table.getColumn("�Ϸ� ����").setPreferredWidth(50);
      	table.getColumn("����").setPreferredWidth(50);
      	      	
        area2 = new JPanel(new GridLayout(3,1));
        sel_done = new JButton("���� �Ϸ�");
        sel_change = new JButton("���� ����");
        sel_del = new JButton("���� ����");        
        sel_done.addActionListener(this);
        sel_change.addActionListener(this);
        sel_del.addActionListener(this);
        area2.add(sel_done);
        area2.add(sel_change);
        area2.add(sel_del);
        
        area3 = new JPanel(new GridLayout(2,1));
        buttons = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        buttons.setBackground(Color.green);
        buttons.setPreferredSize(new Dimension(550, 100));
        subtitle1 = new JLabel("�߰�");
        n_lecture = new JButton("���� �Է�");
        n_assign = new JButton("���� �Է�");
        n_sched = new JButton("���� �Է�");
        n_lecture.addActionListener(this);
        n_assign.addActionListener(this);
        n_sched.addActionListener(this);
        buttons.add(n_lecture);
        buttons.add(n_assign);
        buttons.add(n_sched);
        area3.add(subtitle1);
        area3.add(buttons);
        
        area4 = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        refresh = new JButton("���ΰ�ħ");
        refresh.addActionListener(this);
        save = new JButton("����");
        save.addActionListener(this);
        saveandquit = new JButton("���� �� ������");
        saveandquit.addActionListener(this);
        area4.add(refresh);
        area4.add(save);
        area4.add(saveandquit);
        
        //�гκ� ��׶��� ���� ����
        area2.setBackground(Color.red);
        area3.setBackground(Color.green);
        area4.setBackground(Color.blue);
        
        //�г��� ũ�� ����
        area1.setPreferredSize(new Dimension(500, 400));
        area2.setPreferredSize(new Dimension(150, 400));
        area3.setPreferredSize(new Dimension(500, 100));
        area4.setPreferredSize(new Dimension(150, 100));
        
        add(area1);
        add(area2);
        add(area3);
        add(area4);

        setVisible(true);
    }
    String done_stat, type_stat;
    @Override
    public void actionPerformed(ActionEvent e){
		Object obj = e.getSource();
		Dialog d;
		JButton save = new JButton("����");
		JButton cancel = new JButton("���");
		JTextField start_date,end_date,day,content,time;
		JPanel main_panel,data_panel, button_panel;
		JPanel subpanel1,subpanel2,subpanel3;
		if(obj == sel_done) {
			int index = table.getSelectedRow();
			if(index != -1) {
			    data.complete(index);
			    JOptionPane.showMessageDialog(null, "������ �Ϸ��߽��ϴ�.", "���Ϲ�ġ", JOptionPane.INFORMATION_MESSAGE);
			}
			refresh_table();
		} else if (obj == sel_change) {	        
			int index = table.getSelectedRow();	
			if(index != -1) {
				d = new Dialog(this, "���� ����");
				main_panel = new JPanel(new BorderLayout());	
				main_panel.add(new JLabel("���� ����������"),BorderLayout.NORTH);
				
			    String todo[] = data.get(index);
			    
			    data_panel = new JPanel(new GridLayout(3,1));
			    subpanel1 = new JPanel(new GridLayout(3,2));
			    subpanel1.add(new JLabel("���� ����"));
			    content = new JTextField(todo[0],8);
			    subpanel1.add(content);
			    subpanel1.add(new JLabel("����/�˸� ����"));
				start_date = new JTextField(todo[1],8);
				subpanel1.add(start_date);
				subpanel1.add(new JLabel("���� ����"));
				end_date = new JTextField(todo[2],8);
				subpanel1.add(end_date);
				subpanel2 = new JPanel(new GridLayout(1,3));
				subpanel2.add(new JLabel("�ϷῩ��"));
				done = new ButtonGroup();
				pos = new JRadioButton("�Ϸ�");
				neg = new JRadioButton("�̿Ϸ�");
				done.add(pos);
				done.add(neg);
				pos.addItemListener( new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange()==1){
							done_stat = "0";
		                }
					}	            
		        });
				neg.addItemListener( new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange()==1){
							done_stat = "1";
		                }
					}	            
		        });
				subpanel2.add(pos);
				subpanel2.add(neg);
				subpanel3 = new JPanel(new GridLayout(1,4));
				subpanel3.add(new JLabel("���� ����"));
				type = new ButtonGroup();
				lec = new JRadioButton("����");
				ass = new JRadioButton("����");
				sch = new JRadioButton("����");
				type.add(lec);
				type.add(ass);
				type.add(sch);
				lec.addItemListener( new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange()==1){
							type_stat = "0";
		                }
					}	            
		        });
				ass.addItemListener( new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange()==1){
							type_stat = "1";
		                }
					}	            
		        });
				sch.addItemListener( new ItemListener() {
					@Override
					public void itemStateChanged(ItemEvent e) {
						if(e.getStateChange()==1){
							type_stat = "2";
		                }
					}	            
		        });
				subpanel3.add(lec);
				subpanel3.add(ass);
				subpanel3.add(sch);
				data_panel.add(subpanel1);
				data_panel.add(subpanel2);
				data_panel.add(subpanel3);
		        main_panel.add(data_panel,BorderLayout.CENTER);
				
		        button_panel = new JPanel();
		        button_panel.add(save);
		        button_panel.add(cancel);
		        save.addActionListener( new ActionListener(){	            
		            public void actionPerformed(ActionEvent e) {
		            	Calendar start=Calendar.getInstance(),end=Calendar.getInstance();
		            	Date temp;
		            	try {
							temp = date_f.parse(start_date.getText());
							start.setTime(temp);
							temp = date_time_f.parse(end_date.getText());
							end.setTime(temp);
						} catch (ParseException e1) {
							e1.printStackTrace();
						}
		            	
		            	data.change(index, content.getText(),start,end,done_stat,type_stat);  	
		            	
		            	refresh_table();
		            	d.dispose();
		            	JOptionPane.showMessageDialog(null, "������ �����߽��ϴ�.", "���Ϲ�ġ", JOptionPane.INFORMATION_MESSAGE);
		            }	            
		        });
		        cancel.addActionListener( new ActionListener(){	            
		            public void actionPerformed(ActionEvent e) {
		            	d.dispose();
		            }	            
		        });
		        main_panel.add(button_panel, BorderLayout.SOUTH);
		        
				d.add(main_panel);
		        d.setSize(300,250);
		        d.setVisible(true);
			}
		} else if (obj == sel_del){
			int index = table.getSelectedRow();
			if(index != -1) {
			    data.del(index);
			    JOptionPane.showMessageDialog(null, "������ �����߽��ϴ�.", "���Ϲ�ġ", JOptionPane.INFORMATION_MESSAGE);
			}
			refresh_table();
        }else if (obj == n_lecture) {
			d = new Dialog(this, "���� �߰�");
			main_panel = new JPanel(new BorderLayout());
			main_panel.add(new JLabel("���� �߰�������"),BorderLayout.NORTH);
	        
			data_panel = new JPanel(new GridLayout(5,2));
			data_panel.add(new JLabel("������"));
			start_date = new JTextField("yyyy-MM-dd",8);
			data_panel.add(start_date);
			data_panel.add(new JLabel("������"));
			end_date = new JTextField("yyyy-MM-dd",8);
			data_panel.add(end_date);
			data_panel.add(new JLabel("����"));
			day = new JTextField("��/��",8);
			data_panel.add(day);
			data_panel.add(new JLabel("�����"));
			content = new JTextField("�����",8);
			data_panel.add(content);
			data_panel.add(new JLabel("����"));
			time = new JTextField("1~1/3~4",8);
			data_panel.add(time);
	        main_panel.add(data_panel,BorderLayout.CENTER);
			
	        button_panel = new JPanel();
	        button_panel.add(save);
	        button_panel.add(cancel);
	        save.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {
	            	Calendar start=Calendar.getInstance(),end=Calendar.getInstance();
	            	Calendar start_time=Calendar.getInstance(),end_time=Calendar.getInstance();
	            	Date temp;
	            	String weeks = " �Ͽ�ȭ�������";

	            	try {
						temp = date_f.parse(start_date.getText());
						start.setTime(temp);
						temp = date_f.parse(end_date.getText());
						end.setTime(temp);
						end.set(Calendar.HOUR_OF_DAY,23);
						end.set(Calendar.MINUTE,59);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}	            	
	            	
	            	String days[] = day.getText().split("/"); //���� �������� �� ������
	            	int count = days.length;
	            	int days_i[] = new int[count];      //���� ���ڷ� ��ȯ
	            	for(int i = 0; i < count;i++) {
	            		days_i[i] = weeks.indexOf(days[i]);
	            	}
	            	
	            	String time1[] = time.getText().split("/"); //���Ϻ��� �ð� ������
	            	String time2[] = new String[2]; //�� �ð����� ���� �� ������
	            	int time3[][] = new int[count][2]; //���� �ð� ������ ��� �迭
	            	for(int i = 0; i < count;i++) {
	            	    time2 = time1[i].split("~");
	            	    time3[i][0]=Integer.parseInt(time2[0])+8;//���۽ð�
	            	    time3[i][1]=Integer.parseInt(time2[1])+9;//���ð�
	            	}
	            	
	            	for(Calendar date = start; date.before(end) || date.equals(end); date.add(Calendar.DATE, 1)) {
	            		for(int i = 0; i < count; i++) {
	            			if(date.get(Calendar.DAY_OF_WEEK) == days_i[i]) {
	            				date.set(Calendar.HOUR_OF_DAY, time3[i][0]);           				
		            			start_time.setTime(date.getTime());
		            			date.set(Calendar.HOUR_OF_DAY, time3[i][1]);
		            			end_time.setTime(date.getTime());
		            			data.add(content.getText(),start_time,end_time,"0","0");
		            		}
	            		}
	            	}
	            	refresh_table();
	            	d.dispose();
	            }	            
	        });
	        cancel.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {
	            	d.dispose();
	            }	            
	        });
	        main_panel.add(button_panel, BorderLayout.SOUTH);
	        
			d.add(main_panel);
	        d.setSize(300,250);
	        d.setVisible(true);
		} else if (obj == n_assign) {
			Calendar today_date = Calendar.getInstance();
			String today = date_f.format(today_date.getTime());
			
			d = new Dialog(this, "���� �߰�");
			main_panel = new JPanel(new BorderLayout());
			main_panel.add(new JLabel("���� �߰�������"),BorderLayout.NORTH);
	        
			data_panel = new JPanel(new GridLayout(5,2));
			data_panel.add(new JLabel("���� ������"));
			start_date = new JTextField(today,8);
			data_panel.add(start_date);
			data_panel.add(new JLabel("���� �����Ͻ�"));
			end_date = new JTextField("yyyy-MM-dd HH:mm",8);
			data_panel.add(end_date);
			data_panel.add(new JLabel("���� ����"));
			content = new JTextField("���� ����",8);
			data_panel.add(content);
	        main_panel.add(data_panel,BorderLayout.CENTER);
			
	        button_panel = new JPanel();
	        button_panel.add(save);
	        button_panel.add(cancel);
	        save.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {
	            	Calendar start=Calendar.getInstance(),end=Calendar.getInstance();
	            	Date temp;
	            	try {
						temp = date_f.parse(start_date.getText());
						start.setTime(temp);
						temp = date_time_f.parse(end_date.getText());
						end.setTime(temp);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
	            	data.add(content.getText(),start,end,"0","1");
	            	
	            	refresh_table();
	            	d.dispose();
	            }	            
	        });
	        cancel.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {
	            	d.dispose();
	            }	            
	        });
	        main_panel.add(button_panel, BorderLayout.SOUTH);
	        
			d.add(main_panel);
	        d.setSize(300,200);
	        d.setVisible(true);
		} else if (obj == n_sched) {
			Calendar today_date = Calendar.getInstance();
			String today = date_f.format(today_date.getTime());
			
			d = new Dialog(this, "���� �߰�");
			main_panel = new JPanel(new BorderLayout());
			main_panel.add(new JLabel("���� �߰� ������"),BorderLayout.NORTH);
	        
			data_panel = new JPanel(new GridLayout(5,2));
			data_panel.add(new JLabel("������ �Ͻ�"));
			end_date = new JTextField("yyyy-MM-dd HH:mm",8);
			data_panel.add(end_date);
			data_panel.add(new JLabel("�˸��� ������ ��"));
			start_date = new JTextField(today,8);
			data_panel.add(start_date);
			data_panel.add(new JLabel("������ ����"));
			content = new JTextField("������ ����",8);
			data_panel.add(content);
	        main_panel.add(data_panel,BorderLayout.CENTER);
			
	        button_panel = new JPanel();
	        button_panel.add(save);
	        button_panel.add(cancel);
	        save.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {
	            	Calendar start=Calendar.getInstance(),end=Calendar.getInstance();
	            	Date temp;
	            	try {
						temp = date_f.parse(start_date.getText());
						start.setTime(temp);
						temp = date_time_f.parse(end_date.getText());
						end.setTime(temp);
					} catch (ParseException e1) {
						e1.printStackTrace();
					}
	            	data.add(content.getText(),start,end,"0","2");
	            	
	            	refresh_table();
	            	d.dispose();
	            }	            
	        });
	        cancel.addActionListener( new ActionListener(){	            
	            public void actionPerformed(ActionEvent e) {
	            	d.dispose();
	            }	            
	        });
	        main_panel.add(button_panel, BorderLayout.SOUTH);
	        
			d.add(main_panel);
	        d.setSize(300,200);
	        d.setVisible(true);
		} else if (obj == refresh) {
			refresh_table();
		} else if (obj == save) {
			JOptionPane.showMessageDialog(null, "���� �� ����˴ϴ�.", "���Ϲ�ġ", JOptionPane.INFORMATION_MESSAGE);
			data.save();
			JOptionPane.showMessageDialog(null, "������ �Ϸ�Ǿ����ϴ�.", "���Ϲ�ġ", JOptionPane.INFORMATION_MESSAGE);
		} else if (obj == saveandquit) {
			JOptionPane.showMessageDialog(null, "���� �� ����˴ϴ�.", "���Ϲ�ġ", JOptionPane.INFORMATION_MESSAGE);
			data.save();
			System.exit(0);
		}
    }
    public void refresh_table() {
    	try {
			Calendar target = Calendar.getInstance();
			String todo_data[][] = data.read(target);
			model.setNumRows(0);
			for(int i=0; i<todo_data.length; i++) {
				model.addRow(todo_data[i]);
			}
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
    }
}