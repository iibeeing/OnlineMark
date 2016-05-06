package com.cx.test.service;

import javax.annotation.Resource;

import org.aspectj.lang.annotation.Before;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;

import com.cx.service.interfaces.IAuthorityService;
import com.cx.service.interfaces.IRoleService;

//@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:springcontext-config-test.xml")
@TransactionConfiguration(transactionManager = "h4TManager", defaultRollback = true)
public class commontest extends AbstractJUnit4SpringContextTests {
	
//	@Before  
    public void setUp() throws Exception  
    {  
    }  
	
	@Resource
    private IRoleService roleService;
	
	@Resource
    private IAuthorityService authorityService;

//	@Test
	public void testChain(){
		
		//List<Authority> allAuthority =authorityService.listChain();
		
		
		String ddd="";
		ddd="111";
		
	}

	/*@Test
    public void testSave() {
		
		Role role=new Role();
    	role.setEnable(true);
    	//role.setName("AAA");
    	role.setName("CCCCCCC"+Calendar.getInstance().get(Calendar.SECOND));
    	role.setPosition(0);
    	role.setTheValue("eee");
			try {
				roleService.save(role);

			} catch (EntityOperateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ValidatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}*/
	
	//@Ignore  
    public void testOtherSpringObject()  
    {  
        
    }  

}

