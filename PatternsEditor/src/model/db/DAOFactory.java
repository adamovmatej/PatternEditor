package model.db;

public final class DAOFactory {

	private static DAOFactory factory = new DAOFactory();
	
	
	private DAOFactory(){
	}
	
	public static DAOFactory getInstance(){
		return factory;
	}
	
	public PatternDAO getPatternDAO(){
		return new PatternDAO();
	}
	
	public AdapterDAO getAdapterDAO(){
		return new AdapterDAO();
	}
}
