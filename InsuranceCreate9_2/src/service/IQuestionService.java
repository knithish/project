package service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import bean.ClaimBean;
import bean.PolicyDetailsBean;
import bean.QuestionBean;
import exception.QuestionException;

public interface IQuestionService
{
	boolean validateDetails(ClaimBean claimBean) throws QuestionException;

	public String insertClaim(ClaimBean claimNumber) throws SQLException, QuestionException, ClassNotFoundException, Exception;
	
	public List<QuestionBean> getQuestions(String businessSegment) throws ClassNotFoundException, IOException, SQLException;
	
	public List<PolicyDetailsBean> getPolicyDetails(PolicyDetailsBean policyDetailsBean) throws ClassNotFoundException, SQLException, Exception;

	public String getBusinessSegment(String name) throws ClassNotFoundException, IOException, SQLException;
}
