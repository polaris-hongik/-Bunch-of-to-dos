package project_todo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;

//벡터 2중 ArrayList 이용해서 구현 예정
public class Data {
	private ArrayList<Todo> todolist = new ArrayList<Todo>();
	private int size_of_todo = 5;
	
	private SimpleDateFormat date_time_f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private String complete_stat[] = {"미완료","완료","샘플"};
	private String type_stat[] = {"수업","과제","일정","샘플"};

	public Data() {
		File file = new File("todolist");
		try{
	        //입력 스트림 생성
	        FileReader filereader = new FileReader(file);
	        //입력 버퍼 생성
	        BufferedReader bufReader = new BufferedReader(filereader);
            String line = "";
            while((line = bufReader.readLine()) != null){
                String[] data = line.split(";");
                ArrayList<String> todo = new ArrayList<String>();
                for(int i = 0; i<data.length ; i++ ) {
                	todo.add(data[i]);
                }
                todolist.add(new Todo(todo));
            }
            filereader.close();
            bufReader.close();
        }catch (FileNotFoundException e) {
        	try {
				file.createNewFile();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
        }catch(IOException e){
            e.printStackTrace();
        }
	}
	public void add(String content,Calendar start_time,Calendar end_time,String done,String type) {
		ArrayList<String> todo = new ArrayList<String>(); 
		todo.add(content);
		todo.add(date_time_f.format(start_time.getTime()));
		todo.add(date_time_f.format(end_time.getTime()));
		todo.add(done);
		todo.add(type);
		todolist.add(new Todo(todo));
		Collections.sort(todolist);
	}
	public void del(int i) {
		todolist.remove(i);
	}
	public void change(int i, String content,Calendar start_time,Calendar end_time,String done,String type) {
		todolist.remove(i);
		ArrayList<String> todo = new ArrayList<String>(); 
		todo.add(content);
		todo.add(date_time_f.format(start_time.getTime()));
		todo.add(date_time_f.format(end_time.getTime()));
		todo.add(done);
		todo.add(type);
		todolist.add(new Todo(todo));
		Collections.sort(todolist);
	}
	public String[] get(int i) {
		Todo todo = todolist.get(i);
	    String result[] = todo.toList().toArray(new String[0]);
	    result[3] = complete_stat[todo.get_done()];
    	result[4] = type_stat[todo.get_type()];
		return result;
	}
	public void complete(int i) {
	    Todo todo = todolist.get(i);
	    todo.set_done(1);
	    todolist.remove(i);
	    todolist.add(i,todo);
	}
	public String[][] read(Calendar target_day) throws ParseException {
		int size = todolist.size();
		target_day.set(Calendar.HOUR_OF_DAY, 00);
		target_day.set(Calendar.MINUTE, 00);
		target_day.set(Calendar.SECOND, 00);
		target_day.set(Calendar.MILLISECOND, 00);
		Calendar start = Calendar.getInstance(),end = Calendar.getInstance();
		String target_todo[][] = new String[size][size_of_todo];

		int count = 0;
		
		for(int i = 0; i < size; i++ ){			
			Todo todo = todolist.get(i);
		    start = todo.get_start_date();
		    end = todo.get_end_date();
		    
		    if((target_day.after(start) || target_day.equals(start)) && (target_day.before(end) || target_day.equals(end))){
		    	target_todo[count]=todo.toList().toArray(new String[0]);
		    	target_todo[count][3] = complete_stat[todo.get_done()];
		    	target_todo[count][4] = type_stat[todo.get_type()];
		    	count++;
		    }
		}
        String result[][] = new String [count][size_of_todo];
        System.arraycopy(target_todo, 0, result, 0, count);
		return result;
	}
	public void save() {
		int size = todolist.size();
		File file = new File("todolist");
		try {
			FileWriter filewriter = new FileWriter(file);
		    BufferedWriter writer = new BufferedWriter(filewriter);
		    for(int i = 0; i < size; i++) {
		    	Todo todo = todolist.get(i);
		    	StringBuffer temp = new StringBuffer();
		    	for(int j = 0; j < size_of_todo; j++) {
		    		temp.append(todo.toList().get(j));
		    		if(j!=size_of_todo-1) {
			    		temp.append(";");	
		    		}
		    	}
		        String line = temp.toString();
		        System.out.println();
		        writer.write(line);
		        writer.newLine();
		    }
		    writer.close();
		    filewriter.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

}
