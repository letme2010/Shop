package com.cn.shop.action;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.cn.shop.common.Constants;
import com.cn.shop.dto.UserDTO;
import com.cn.shop.model.User;
import com.cn.shop.service.UserService;
import com.cn.shop.util.EmailAttachmentSender;
import com.cn.shop.util.ProductCode;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RegisterAction extends ActionSupport implements ModelDriven  {
	
	private UserDTO userDTO = new UserDTO( );
	
	private User user = new User( );
	
	private UserService userService;
	
	//���session
	private Map<String, Object> session;
	

	
	//�ʼ�ע��
	
	private EmailAttachmentSender emailSender = new EmailAttachmentSender( );
	
	private String username;
	
	//��̬��֤��
	
	private HttpServletRequest request;
	
	private HttpServletResponse response;
	
	
	
	
	//�û�ע��
	
	
	public String userRegister( )
	{
		
		session = (Map)ActionContext.getContext().getSession();
		
		//�޸�bug �޸�session��ò��ȶ��� 
		
		/*String rand = ""; 
				
		rand = (String) request.getSession().getAttribute("rand");
		
		if( rand == null )
		{
			rand =" ";
		}
		
		System.out.println("��֤��"+ rand);
		
		
		
		
		String ret = "";
		
		int p1 = 0+ userDTO.getPassword1();
		
		int p2 = 0+  userDTO.getPassword2();
		
		if(( p1 != p2 ) || ( p1 == 0 )  )
		{
			ret = "reset"; //���½���ע��ҳ��
		}
		//else if( (!userDTO.getRand().equals(rand ) ) || userDTO.getRand() == null  )
		
		
		//bug 
		
		else if( ( !rand.equals(userDTO.getRand() )  ) ||  userDTO.getRand() == null  )
		{
			ret = "reset"; //���½���ע��ҳ��
		}*/
		
		
				//session һֱ��bug ע�� 2014.8.30
		
				String rand = (String) request.getSession().getAttribute("rand");
				
				System.out.println("��֤��"+ rand);
				
					
				String ret = "";
				
				if(( userDTO.getPassword1() != userDTO.getPassword2() )  )
				{
					ret = "reset"; //���½���ע��ҳ��
				}
				//else if( (!userDTO.getRand().equals(rand ) ) || userDTO.getRand() == null  )
				
				//�޸�bug 2014.8.29
				else if( (!rand.equals(userDTO.getRand())) || userDTO.getRand() == null )
				{
					ret = "reset"; //���½���ע��ҳ��
				}
				else if( ( !rand.equals(userDTO.getRand() )  ) ||  userDTO.getRand() == null  )
				{
					ret = "reset"; //���½���ע��ҳ��
				}
			
		
		else if( ( userDTO.getEmail() != null ) && ( userDTO.getName() !=null )  ) 
		{
			
			user.set(userDTO);
			
			session.put(user.getName(),  user );
			
		
			
			//�����ʼ�
			
			emailSender.send(user.getEmail(), user.getName() );
			
			ret ="verify"; //��¼������֤
						
			
		}
		else
		{
			ret = "reset"; //���½���ע��ҳ��
		}
		
						
		return ret;
		
		
		
		
	}
	
	public String usersave( )
	{
		
		session = (Map)ActionContext.getContext().getSession();
		
		
		User u = (User) session.get( username );
		
	
		
		
		System.out.println( username + u.getEmail() );
		
		
		
		userService.add(u);
		
		return "save";
		
		
		
	}
	
	public void  code( )
	{
		request = ServletActionContext.getRequest();
		
		response = ServletActionContext.getResponse();
		
		ProductCode.productCode(request, response);
		
		
	}
	

	@Override
	public Object getModel() {
		// TODO Auto-generated method stub
		return userDTO;
	}

	
	
	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
	
	
	
	

}
