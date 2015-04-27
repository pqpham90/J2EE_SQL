package assign.services;

public interface MyEavesDropService {
	int getMeetingCounts(String meeting, String year) throws Exception;

	String getMeetings(String meeting, String year) throws Exception;

	boolean checkMeeting(String meeting) throws Exception;

	boolean checkYearsExist(String meeting) throws  Exception;
}
