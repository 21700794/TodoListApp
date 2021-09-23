package com.todo.service;

import java.io.*;
import java.util.*;
import com.todo.dao.TodoItem;
import com.todo.dao.TodoList;

public class TodoUtil {
	
	public static void createItem(TodoList list) {
		
		String title, desc;
		Scanner sc = new Scanner(System.in);
		
		System.out.println("항목추가\n" + "[제목] : ");
		
		title = sc.next();
		if (list.isDuplicate(title)) {
			System.out.printf("중복 불가!");
			return;
		}
		sc.nextLine();
		System.out.println("[내용] : ");
		desc = sc.nextLine().trim();
		
		TodoItem t = new TodoItem(title, desc);
		list.addItem(t);
		System.out.println("추가되었습니다.");
	}

	public static void deleteItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 삭제]\n");
		System.out.println("삭제하고 싶은 제목을 선택하십시오 : ");
		String title = sc.next();
		
		for (TodoItem item : l.getList()) {
			if (title.equals(item.getTitle())) {
				l.deleteItem(item);
				System.out.println("삭제되었습니다.");
				break;
			}
		}
	}


	public static void updateItem(TodoList l) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("[항목 수정]\n"
				+ "수정하고 싶은 제목을 선택하십시오 : ");
		String title = sc.next().trim();
		if (!l.isDuplicate(title)) {
			System.out.println("없는 제목입니다.");
			return;
		}

		System.out.println("[수정 제목] : ");
		String new_title = sc.next().trim();
		if (l.isDuplicate(new_title)) {
			System.out.println("중복 불가!");
			return;
		}
		
		System.out.println("[수정 내용] : ");
		String new_description = sc.next().trim();
		for (TodoItem item : l.getList()) {
			if (item.getTitle().equals(title)) {
				l.deleteItem(item);
				TodoItem t = new TodoItem(new_title, new_description);
				l.addItem(t);
				System.out.println("수정되었습니다.");
			}
		}

	}

	public static void listAll(TodoList l) {
		for (TodoItem item : l.getList()) {
			System.out.println(item.toString());
		}
	}
	
	public static void saveList(TodoList l, String filename) {
		try { 
			Writer w = new FileWriter("todolist.txt");
			for(TodoItem item : l.getList()) {
				w.write(item.toSaveString());
			}
			
			System.out.println("정보 저장하였습니다.");
			w.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public static void loadList(TodoList l, String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader("todolist.txt"));
			
			String oneline;
			while((oneline = br.readLine()) != null) {
				StringTokenizer st = new StringTokenizer(oneline, "##");
				String title = st.nextToken();
				String desc = st.nextToken();
				String current_date = st.nextToken();
				
				
				TodoItem t = new TodoItem(title, desc, current_date);
				l.addItem(t);
			}
			br.close();
			System.out.println("로딩 완료하였습니다.");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
