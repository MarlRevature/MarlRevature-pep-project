package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
public class AccountDAO {
    public Account accountRegistration(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            if (!account.getUsername().isEmpty() && account.getPassword().length()>=4){
                String sql = "insert into account (username,password) values (?,?)" ;
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, account.getUsername());
                ps.setString(2, account.getPassword());

                ps.executeUpdate();
                ResultSet pkeyResultSet = ps.getGeneratedKeys();
                if(pkeyResultSet.next()){
                    int generated_account_id = pkeyResultSet.getInt(1);
                    account.setAccount_id(generated_account_id);
                }
                return account;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    public Account getAccount(Account account)
    {
        Connection connection = ConnectionUtil.getConnection();
        try {
            String sql = "select * from account where username=? and password=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, account.getUsername());
            ps.setString(2, account.getPassword());

            //write preparedStatement's setInt method here.
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                account.setAccount_id(rs.getInt("account_id"));
                return account;
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return null;
    }
}
