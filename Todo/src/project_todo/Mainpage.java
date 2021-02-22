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
	String[] colNames = {"할일", "시작 시간", "종료 시간", "완료 여부", "종류"};
	DefaultTableModel model;
	SimpleDateFormat date_f = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat date_time_f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	
    public Mainpage(String str, Data data) throws ParseException{
        super(str);  //프레임의 타이틀
        this.data = data;
        setSize(750,600);        //컨테이너 크기 지정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //기본 레이아웃을 FlowLayout으로 지정    좌우 간격 20px, 상하 간격 50px
        setLayout(new FlowLayout(FlowLayout.LEFT,20,20));
        
      	//표에 들어갈 데이터들.. /처음엔 빈 테이블 만들기 위해.. 데이터관리객체 생성
        Calendar target = Calendar.getInstance();
      	String todo_data[][] = data.read(target);
        model = new DefaultTableModel(todo_data,colNames);
        
      	area1 = new JPanel(new GridLayout(1,1));
      	table = new JTable(model);
      	scrollPane = new JScrollPane(table);
      	area1.add(scrollPane);
      	table.getColumn("할일").setPreferredWidth(200);
      	table.getColumn("시작 시간").setPreferredWidth(50);
      	table.getColumn("종료 시간").setPreferredWidth(50);
      	table.getColumn("완료 여부").setPreferredWidth(50);
      	table.getColumn("종류").setPreferredWidth(50);
      	      	
        area2 = new JPanel(new GridLayout(3,1));
        sel_done = new JButton("일정 완료");
        sel_change = new JButton("일정 수정");
        sel_del = new JButton("일정 삭제");        
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
        subtitle1 = new JLabel("추가");
        n_lecture = new JButton("수업 입력");
        n_assign = new JButton("과제 입력");
        n_sched = new JButton("일정 입력");
        n_lecture.addActionListener(this);
        n_assign.addActionListener(this);
        n_sched.addActionListener(this);
        buttons.add(n_lecture);
        buttons.add(n_assign);
        buttons.add(n_sched);
        area3.add(subtitle1);
        area3.add(buttons);
        
        area4 = new JPanel(new FlowLayout(FlowLayout.CENTER,5,5));
        refresh = new JButton("새로고침");
        refresh.addActionListener(this);
        save = new JButton("저장");
        save.addActionListener(this);
        saveandquit = new JButton("저장 후 나가기");
        saveandquit.addActionListener(this);
        area4.add(refresh);
        area4.add(save);
        area4.add(saveandquit);
        
        //패널별 백그라운드 색상 지정
        area2.setBackground(Color.red);
        area3.setBackground(Color.green);
        area4.setBackground(Color.blue);
        
        //패널의 크기 지정
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
		JButton save = new JButton("저장");
		JButton cancel = new JButton("취소");
		JTextField start_date,end_date,day,content,time;
		JPanel main_panel,data_panel, button_panel;
		JPanel subpanel1,subpanel2,subpanel3;
		if(obj == sel_done) {
			int index = table.getSelectedRow();
			if(index != -1) {
			    data.complete(index);
			    JOptionPane.showMessageDialog(null, "일정을 완료했습니다.", "할일뭉치", JOptionPane.INFORMATION_MESSAGE);
			}
			refresh_table();
		} else if (obj == sel_change) {	        
			int index = table.getSelectedRow();	
			if(index != -1) {
				d = new Dialog(this, "일정 수정");
				main_panel = new JPanel(new BorderLayout());	
				main_panel.add(new JLabel("일정 수정페이지"),BorderLayout.NORTH);
				
			    String todo[] = data.get(index);
			    
			    data_panel = new JPanel(new GridLayout(3,1));
			    subpanel1 = new JPanel(new GridLayout(3,2));
			    subpanel1.add(new JLabel("일정 내용"));
			    content = new JTextField(todo[0],8);
			    subpanel1.add(content);
			    subpanel1.add(new JLabel("일정/알림 시작"));
				start_date = new JTextField(todo[1],8);
				subpanel1.add(start_date);
				subpanel1.add(new JLabel("일정 마감"));
				end_date = new JTextField(todo[2],8);
				subpanel1.add(end_date);
				subpanel2 = new JPanel(new GridLayout(1,3));
				subpanel2.add(new JLabel("완료여부"));
				done = new ButtonGroup();
				pos = new JRadioButton("완료");
				neg = new JRadioButton("미완료");
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
				subpanel3.add(new JLabel("일정 종류"));
				type = new ButtonGroup();
				lec = new JRadioButton("수업");
				ass = new JRadioButton("과제");
				sch = new JRadioButton("일정");
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
		            	JOptionPane.showMessageDialog(null, "일정을 수정했습니다.", "할일뭉치", JOptionPane.INFORMATION_MESSAGE);
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
			    JOptionPane.showMessageDialog(null, "일정을 삭제했습니다.", "할일뭉치", JOptionPane.INFORMATION_MESSAGE);
			}
			refresh_table();
        }else if (obj == n_lecture) {
			d = new Dialog(this, "수업 추가");
			main_panel = new JPanel(new BorderLayout());
			main_panel.add(new JLabel("수업 추가페이지"),BorderLayout.NORTH);
	        
			data_panel = new JPanel(new GridLayout(5,2));
			data_panel.add(new JLabel("개강일"));
			start_date = new JTextField("yyyy-MM-dd",8);
			data_panel.add(start_date);
			data_panel.add(new JLabel("종강일"));
			end_date = new JTextField("yyyy-MM-dd",8);
			data_panel.add(end_date);
			data_panel.add(new JLabel("요일"));
			day = new JTextField("월/목",8);
			data_panel.add(day);
			data_panel.add(new JLabel("과목명"));
			content = new JTextField("과목명",8);
			data_panel.add(content);
			data_panel.add(new JLabel("교시"));
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
	            	String weeks = " 일월화수목금토";

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
	            	
	            	String days[] = day.getText().split("/"); //요일 여러개일 시 나누기
	            	int count = days.length;
	            	int days_i[] = new int[count];      //요일 숫자로 변환
	            	for(int i = 0; i < count;i++) {
	            		days_i[i] = weeks.indexOf(days[i]);
	            	}
	            	
	            	String time1[] = time.getText().split("/"); //요일별로 시간 나누기
	            	String time2[] = new String[2]; //각 시간별로 시작 끝 나누기
	            	int time3[][] = new int[count][2]; //최종 시간 데이터 담길 배열
	            	for(int i = 0; i < count;i++) {
	            	    time2 = time1[i].split("~");
	            	    time3[i][0]=Integer.parseInt(time2[0])+8;//시작시간
	            	    time3[i][1]=Integer.parseInt(time2[1])+9;//끝시간
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
			
			d = new Dialog(this, "과제 추가");
			main_panel = new JPanel(new BorderLayout());
			main_panel.add(new JLabel("과제 추가페이지"),BorderLayout.NORTH);
	        
			data_panel = new JPanel(new GridLayout(5,2));
			data_panel.add(new JLabel("과제 생성일"));
			start_date = new JTextField(today,8);
			data_panel.add(start_date);
			data_panel.add(new JLabel("과제 만료일시"));
			end_date = new JTextField("yyyy-MM-dd HH:mm",8);
			data_panel.add(end_date);
			data_panel.add(new JLabel("과제 개요"));
			content = new JTextField("과제 설명",8);
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
			
			d = new Dialog(this, "일정 추가");
			main_panel = new JPanel(new BorderLayout());
			main_panel.add(new JLabel("일정 추가 페이지"),BorderLayout.NORTH);
	        
			data_panel = new JPanel(new GridLayout(5,2));
			data_panel.add(new JLabel("스케쥴 일시"));
			end_date = new JTextField("yyyy-MM-dd HH:mm",8);
			data_panel.add(end_date);
			data_panel.add(new JLabel("알리기 시작할 날"));
			start_date = new JTextField(today,8);
			data_panel.add(start_date);
			data_panel.add(new JLabel("스케쥴 개요"));
			content = new JTextField("스케쥴 설명",8);
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
			JOptionPane.showMessageDialog(null, "저장 후 종료됩니다.", "할일뭉치", JOptionPane.INFORMATION_MESSAGE);
			data.save();
			JOptionPane.showMessageDialog(null, "저장이 완료되었습니다.", "할일뭉치", JOptionPane.INFORMATION_MESSAGE);
		} else if (obj == saveandquit) {
			JOptionPane.showMessageDialog(null, "저장 후 종료됩니다.", "할일뭉치", JOptionPane.INFORMATION_MESSAGE);
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