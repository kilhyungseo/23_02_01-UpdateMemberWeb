package com.hyungseo.exe;

import java.sql.*;



public class MemberDao {

	String driverName = "com.mysql.cj.jdbc.Driver";
	String url = "jdbc:mysql://localhost:3306/webdb";
	String username = "root";
	String password = "1234";
	
	public int joinMember(String id, String pw, String name, String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "INSERT INTO members(id, password, name, email) VALUES (?,?,?,?)";
		int resultFlag = 0;
		
		try {
	         Class.forName(driverName);//드라이버 불러오기
	         conn = DriverManager.getConnection(url, username, password);//DB 연동
	         
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);
				pstmt.setString(2, pw);
				pstmt.setString(3, name);
				pstmt.setString(4, email);

				resultFlag = pstmt.executeUpdate(); //성공하면 1로 값이 변경
			
	      } catch(Exception e) {
	         e.printStackTrace();
	      }	finally {
	    	  try {
	    		  if(pstmt != null) {
	    			  pstmt.close();
	    		  }
	    		  if(conn != null) {
	    			  conn.close();
	    		  }
	    	  }catch(Exception e2) {
	    		  e2.printStackTrace();
	    	  }
	      }
			return resultFlag;
	}
	
	public int checkId(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM members WHERE id=?";
		int resultFlag = 0;
		
		try {
	         Class.forName(driverName);//드라이버 불러오기
	         conn = DriverManager.getConnection(url, username, password);//DB 연동
	         
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);


		 rs = pstmt.executeQuery(); //ResultSet 객체인 rs로 받기
		 
		 if(rs.next()) {
			 resultFlag = 1; //가입하려는 아이디가 존재
		 }else {
			 resultFlag = 0; //가입하려는 아이디가 존재하지 않음
		 }
			
	      } catch(Exception e) {
	         e.printStackTrace();
	      }	finally {
	    	  try {
	    		  if(rs != null) {
	    			  rs.close();
	    		  }
	    		  if(pstmt != null) {
	    			  pstmt.close();
	    		  }
	    		  if(conn != null) {
	    			  conn.close();
	    		  }
	    	  }catch(Exception e2) {
	    		  e2.printStackTrace();
	    	  }
	      }
			return resultFlag;
	}
	
	public int loginCheck(String id, String pw) {
		//int checkId = checkId(id); //1이면 가입된 아이디 존재
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM members WHERE id=?";
		int resultFlag = 0;
		String dbPw;
		
		try {
	         Class.forName(driverName);//드라이버 불러오기
	         conn = DriverManager.getConnection(url, username, password);//DB 연동
	         
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);


		 rs = pstmt.executeQuery(); //ResultSet 객체인 rs로 받기
		 
		 if(rs.next()) {
			 dbPw = rs.getString("password");
			 if(dbPw.equals(pw)) {
				 resultFlag = 1; // 아이디와 비밀번호가 일치 -> 로그인 성공
			 }else {
				 resultFlag = 0; // 아이디는 존재하지만 비밀번호가 불일치 -> 로그인 실패
			 }
			 
		 }else {
			 resultFlag = 0; //가입하려는 아이디가 존재하지 않음
		 }
			
	      } catch(Exception e) {
	         e.printStackTrace();
	      }	finally {
	    	  try {
	    		  if(rs != null) {
	    			  rs.close();
	    		  }
	    		  if(pstmt != null) {
	    			  pstmt.close();
	    		  }
	    		  if(conn != null) {
	    			  conn.close();
	    		  }
	    	  }catch(Exception e2) {
	    		  e2.printStackTrace();
	    	  }
	      }
	
		return resultFlag; // 1이면 로그인 성공, 0이면 로그인 실패
	}
	
	public MemberDto getMemberInfo(String id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "SELECT * FROM members WHERE id=?";
		int resultFlag = 0;
		String dbPw;
		MemberDto dto = null;
		try {
	         Class.forName(driverName);//드라이버 불러오기
	         conn = DriverManager.getConnection(url, username, password);//DB 연동
	         
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, id);


		 rs = pstmt.executeQuery(); //ResultSet 객체인 rs로 받기
		 
		 
		 if(rs.next()) {  //rs로 받은 레코드를 dto 객체에 싣기
			dto = new MemberDto();
			 dto.setId(rs.getString("id"));
			 dto.setPassword(rs.getString("password"));
			 dto.setName(rs.getString("name"));
			 dto.setEmail(rs.getString("email"));
			 dto.setJointime(rs.getTimestamp("jointime"));
		 }
			
	      } catch(Exception e) {
	         e.printStackTrace();
	      }	finally {
	    	  try {
	    		  if(rs != null) {
	    			  rs.close();
	    		  }
	    		  if(pstmt != null) {
	    			  pstmt.close();
	    		  }
	    		  if(conn != null) {
	    			  conn.close();
	    		  }
	    	  }catch(Exception e2) {
	    		  e2.printStackTrace();
	    	  }
	      }
		
		return dto;
	}
	
	public int modify(String id, String pw, String name, String email) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		String sql = "UPDATE members SET password=?, name=?, email=? WHERE id=?";
		int resultFlag = 0;
		
		try {
	         Class.forName(driverName);//드라이버 불러오기
	         conn = DriverManager.getConnection(url, username, password);//DB 연동
	         
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pw);
				pstmt.setString(2, name);
				pstmt.setString(3, email);
				pstmt.setString(4, id);

				resultFlag = pstmt.executeUpdate(); //성공하면 1로 값이 변경
			
	      } catch(Exception e) {
	         e.printStackTrace();
	      }	finally {
	    	  try {
	    		  if(pstmt != null) {
	    			  pstmt.close();
	    		  }
	    		  if(conn != null) {
	    			  conn.close();
	    		  }
	    	  }catch(Exception e2) {
	    		  e2.printStackTrace();
	    	  }
	      }
			return resultFlag;
	}
	public int delOk(String id) {
		String sql = "DELETE FROM members WHERE id = ?";
		
		Connection conn = null;
		PreparedStatement pstmt;
		int resultFlag = 0;
		
		try{
			Class.forName(driverName);//드라이버 불러오기	
			conn = DriverManager.getConnection(url, username, password);//DB 연동
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, id);
			resultFlag = pstmt.executeUpdate(); //삭제 성공하면 1 아니면 다른 값을 반환
						
			if(pstmt != null){
				pstmt.close();
			}
			if(conn != null){
				conn.close();
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultFlag;
	}
	
	
}
