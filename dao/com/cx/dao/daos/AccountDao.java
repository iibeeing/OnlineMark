package com.cx.dao.daos;

import org.springframework.stereotype.Repository;

import com.cx.dao.interfaces.IAccountDao;
import com.cx.model.models.Account;
import com.infrastructure.project.base.dao.daos.EnableEntityDao;

@Repository("AccountDao")
public class AccountDao extends EnableEntityDao<Integer, Account> implements IAccountDao {


}
