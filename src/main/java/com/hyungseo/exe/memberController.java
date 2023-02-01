package com.hyungseo.exe;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.JOptionPane;

/**
 * Servlet implementation class memberController
 */
@WebServlet("*.do")
public class memberController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public memberController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doAction(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doAction(request, response);
	}

	protected void doAction(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri =request.getRequestURI();
		String conPath = request.getContextPath(); // 컨택스트 패스만 분리하여 저장
		String command =uri.substring(conPath.length());
		
		System.out.println(command);
		String viewPage = "/main.jsp";
		if(command.equals("/main.do")) {
			viewPage = "/main.jsp";
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/main.jsp");
//			dispatcher.forward(request, response);
			
		}else if(command.equals("/join.do")){
			viewPage = "/join.jsp";
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/join.jsp");
//			dispatcher.forward(request, response);
			
		}else if(command.equals("/login.do")) {
			viewPage = "/login.jsp";
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/login.jsp");
//			dispatcher.forward(request, response);
			
		}else if(command.equals("/logout.do")) {
			HttpSession session = request.getSession();
			session.invalidate(); //모든 세션값을 무효화(삭제)
			viewPage = "/login.jsp";
//			RequestDispatcher dispatcher = request.getRequestDispatcher("/logout.jsp");
//			dispatcher.forward(request, response);
			
		}else if(command.equals("/modify.do")) {
			HttpSession session = request.getSession();
			String sessionId = (String)session.getAttribute("memberId");
			MemberDao dao = new MemberDao();
			MemberDto dto = dao.getMemberInfo(sessionId);
			
			request.setAttribute("memberDto", dto);
			viewPage = "/modify.jsp";

			
		}else if(command.equals("/joinOk.do")) {
			request.setCharacterEncoding("utf-8");
			
			String mid = request.getParameter("id");
			String mpw = request.getParameter("pw");
			String mname = request.getParameter("name");
			String memail = request.getParameter("email");
			
			int resultInt = 0;
			
			MemberDao dao = new MemberDao();
			int checkId = dao.checkId(mid); // 1이면 가입하려는 아이디가 이미 존재
			
			if (checkId == 0){
				resultInt = dao.joinMember(mid, mpw, mname, memail); // 1이면 삽입 성공
			}else{
				System.out.println("이미 존재하는 아이디입니다.");
				response.sendRedirect("join.do");
			}
			
			if(resultInt ==1) {
				System.out.println("가입성공!!");
				viewPage = "/login.jsp";
			}else {
				viewPage = "/join.jsp";
			}
						
			} else if(command.equals("/loginOk.do")) {
				request.setCharacterEncoding("utf-8");
				
				String mid = request.getParameter("id");
				String mpw = request.getParameter("pw");
				
				MemberDao dao = new MemberDao();
				
				int loginResult = dao.loginCheck(mid, mpw); // 1이면 로그인 성공, 0이면 로그인 실패
				
				if(loginResult == 1){ //로그인 성공
					HttpSession session = request.getSession();
					session.setAttribute("memberId", mid);
					session.setAttribute("ValidSession", "yes");
					viewPage = "/main.jsp";
				}else {
					System.out.println("로그인 실패");
					viewPage = "/login.jsp";
				}	
			}else if(command.equals("/logout.do")) {
				
				HttpSession session = request.getSession();
				session.invalidate(); //모든 세션값을 무효화(삭제)
				viewPage = "/login.jsp";
				
			}else if(command.equals("/delOk.do")) {
				HttpSession session = request.getSession();
				String sessionId = (String)session.getAttribute("memberId");
				MemberDao dao = new MemberDao();
				int delResult = dao.delOk(sessionId);
				if(delResult == 1){
					session.invalidate();
					System.out.println("삭제 되었습니다.");
				}else{ //삭제 실패
					System.out.println("삭제 실패하였습니다.");
				}
				viewPage = "/login.jsp";
				
			}else if(command.equals("/modifyOk.do")){
				
				request.setCharacterEncoding("utf-8");
			
				String mid = request.getParameter("id");
				String mpw = request.getParameter("pw");
				String mname = request.getParameter("name");
				String memail = request.getParameter("email");
				
				MemberDao dao = new MemberDao();
				int resultFlag = dao.modify(mid, mpw, mname, memail);  // 1이면 수정성공
				
				if(resultFlag == 1 ){
					MemberDto dto = dao.getMemberInfo(mid);
					request.setAttribute("memberDto", dto);
					System.out.println("수정완료되었습니다.");
				}else{
					System.out.println("수정실패하였습니다.");
			
				}
		
				viewPage = "/modifyOk.jsp";
			}
		RequestDispatcher dispatcher = request.getRequestDispatcher(viewPage);
		dispatcher.forward(request, response);
	}

}