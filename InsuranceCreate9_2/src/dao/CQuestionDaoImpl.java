package dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
//import java.util.Iterator;
import java.util.List;

import bean.AccountBean;
import bean.ClaimBean;
import bean.PolicyDetailsBean;
import bean.QuestionBean;
import util.DbConnection;

public class CQuestionDaoImpl implements IQuestionDao
{
	static Connection connection=null;
	static QuestionBean questionBean = new QuestionBean();
	static ClaimBean claimBean = new ClaimBean();
	static AccountBean accountBean=null;
	@Override
	public List<QuestionBean> getQuestions(String businessSegment) throws ClassNotFoundException, IOException, SQLException
	{

		connection = DbConnection.getConnection();
		PreparedStatement preparedStatement=null;
		
		ResultSet resultSet=null;
		
		try
		{
			preparedStatement = connection.prepareStatement(QueryMapper.EXECUTE_QUERY);
			preparedStatement.setString(1, businessSegment);
			resultSet = preparedStatement.executeQuery();
			List<QuestionBean> list = new ArrayList<>();
			//Iterator<QuestionBean> iterator = list.iterator();
			while(resultSet.next())
			{
				questionBean= new QuestionBean();
				questionBean.setQuestionId(resultSet.getString(1));
				questionBean.setBusinessSegment(resultSet.getString(2));
				questionBean.setQuestionNo(resultSet.getInt(3));
				questionBean.setQuestion(resultSet.getString(4));
				questionBean.setAnswer1(resultSet.getString(5));
				questionBean.setAnswerWeightage1(resultSet.getInt(6));
				questionBean.setAnswer2(resultSet.getString(7));
				questionBean.setAnswerWeightage2(resultSet.getInt(8));
				questionBean.setAnswer3(resultSet.getString(9));
				questionBean.setAnswerWeightage3(resultSet.getInt(10));
								
				list.add(questionBean);
			}
			
			return list;
		}catch (Exception e) 
		{
			System.out.println(e);
		}
		
		return null;
	}

	@Override
	public String insertClaim(ClaimBean claimBean) throws Exception
	{
		
		PreparedStatement statement=null;
		ResultSet resultSet = null;
		String claimNumber =  claimBean.getClaimNumber();
		
		try {
			 connection = DbConnection.getConnection();
			 Statement st = connection.createStatement();
				resultSet= st.executeQuery(QueryMapper.GEN_SEQ);
				
				while(resultSet.next()) {
					claimNumber+=resultSet.getString(1);
				}
			 statement=connection.prepareStatement(QueryMapper.INSERT_QUERY);
			 statement.setString(1, claimNumber);
			 statement.setString(2, claimBean.getClaimReason());
			 statement.setString(3, claimBean.getAccidentLocationStreet());
			 statement.setString(4, claimBean.getAccidentCity());
			 statement.setString(5, claimBean.getAccidentState());
			 statement.setString(6, claimBean.getAccidentZip());
			 statement.setString(7, claimBean.getClaimType());
			 statement.setLong(8, claimBean.getPolicyNumber());
			 
			 statement.executeUpdate();
			 
			 
			return claimNumber;
			
		} catch (IOException e) {
			throw new Exception(" sorry no details found");
		}
		//return claimBean ;		
	
	}

	@Override
	public List<PolicyDetailsBean> getPolicyDetails(PolicyDetailsBean policyDetailsBean) throws Exception {
		
		PreparedStatement statement=null;
		ResultSet resultSet = null;
		String claimNumber =  claimBean.getClaimNumber();
		
		try {
			 connection = DbConnection.getConnection();
		statement=connection.prepareStatement(QueryMapper.GET_POLICYDETAILS);
		 resultSet=statement.executeQuery();
		 resultSet.next();
		 //policyDetailsList=new ArrayList<>();
		 policyDetailsBean=new PolicyDetailsBean();
		 policyDetailsBean.setPolicyNumber(resultSet.getInt(1));
		 policyDetailsBean.setQuestionId(resultSet.getString(2));
		 policyDetailsBean.setAnswer(resultSet.getString(3));
		 
		return null;
		} catch (IOException e) {
			throw new Exception(" sorry no details found");
		}
	}

	@Override
	public String getBusinessSegment(String name) throws ClassNotFoundException, IOException, SQLException {
		

		Connection connection = DbConnection.getConnection();
		PreparedStatement preparedStatement=null;
		String business=null;
		ResultSet resultSet=null;
		accountBean=new AccountBean();
		try
		{
			preparedStatement = connection.prepareStatement(QueryMapper.Get_BusinessSegment);
			preparedStatement.setString(1, name);
			resultSet = preparedStatement.executeQuery();
			while(resultSet.next())
			{
				business=accountBean.setBusinessSegment(resultSet.getString(1));
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		return business;
	}

}
