package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    private AccountDAO accountDAO;

    /**
     * no-args constructor for creating a new accountService with a new accountDAO.
     */
    public AccountService(){
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO){
        this.accountDAO = accountDAO;
    }
    public Account registerAccount(Account account){
        return accountDAO.accountRegistration(account);
    }
    public Account login(Account account){
        return accountDAO.getAccount(account);
    }
}
