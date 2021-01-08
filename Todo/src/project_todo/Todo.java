package project_todo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Todo implements Comparable<Todo>{
	//내용: 내용,시작시간,끝시간,종료 여부,타입
	//종료여부: 0-미완료,1-완료,2-샘플,타입: 0-수업, 1-과제, 2-일정,3-샘플
	private String contents;
    private Calendar start_datetime, end_datetime;
    private int done;
    private int type;
    private SimpleDateFormat datetime_f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    private SimpleDateFormat date_f = new SimpleDateFormat("yyyy-MM-dd");
    
    public Todo(ArrayList<String> todo) {   	
    	try {
    		Calendar start = Calendar.getInstance(),end = Calendar.getInstance();
			start.setTime(datetime_f.parse(todo.get(1)));
			end.setTime(datetime_f.parse(todo.get(2)));
			this.contents = todo.get(0);
	        this.start_datetime = start;
	        this.end_datetime = end;
	        this.done = Integer.parseInt(todo.get(3));
	        this.type = Integer.parseInt(todo.get(4));
		} catch (ParseException e) {
			e.printStackTrace();
		}   	
    }

    public Todo(String contents, Calendar start_datetime, Calendar end_datetime, int done,int type) {
    	this.contents = contents;
        this.start_datetime = start_datetime;
        this.end_datetime = end_datetime;
        this.done = done;
        this.type = type;
    }
    public int compareTo(Todo t) { //매개변수 하나만 가능
		if(this.get_end_date().before(t.get_end_date())) {
        	return -1;
        }else if(this.get_end_date().after(t.get_end_date())) {
        	return 1;
        }else {
        	if(this.end_datetime.before(t.end_datetime)) {
            	return -1;
            }else if(this.end_datetime.after(t.end_datetime)) {
            	return 1;
            }else {
            	if(this.done > t.done) {
            		return 1;
            	} else if(this.done < t.done) {
            		return -1;
            	}else {
            		if(this.type>t.type) {
        				return 1;
        			}else if(this.type<t.type) {
        				return -1;
        			}else {
        				return 0;
        			}
            	}
            }
        }
    }
    public ArrayList<String> toList() {
    	ArrayList<String> result = new ArrayList<String>();
    	result.add(this.contents);
    	result.add(datetime_f.format(this.start_datetime.getTime()));
    	result.add(datetime_f.format(this.end_datetime.getTime()));
    	result.add(Integer.toString(this.done));
    	result.add(Integer.toString(this.type));
    	return result;
    }
    public String get_contents() { return contents; }
    public Calendar get_start_datetime() { return start_datetime; }
    public Calendar get_end_datetime() { return end_datetime; }
    public Calendar get_start_date() {
    	try {
    		String date_s = date_f.format(this.start_datetime.getTime());
    		Calendar date = Calendar.getInstance();
			date.setTime(date_f.parse(date_s));
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return start_datetime;
    }
    public Calendar get_end_date() {
    	try {
    		String date_s = date_f.format(this.end_datetime.getTime());
    		Calendar date = Calendar.getInstance();
			date.setTime(date_f.parse(date_s));
			return date;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return end_datetime;
    }
    public int get_done() { return done; }
    public int get_type() { return type; }
    
    public void set_contents(String contents) { this.contents = contents; }
    public void set_start_datetime(Calendar start_datetime) { this.start_datetime = start_datetime; }
    public void set_end_datetime(Calendar end_datetime) { this.end_datetime = end_datetime; }
    public void set_done(int done) { this.done = done; }
    public void set_type(int type) { this.type = type; }
}
