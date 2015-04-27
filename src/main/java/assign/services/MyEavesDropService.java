package assign.services;

public interface MyEavesDropService {
	int getMeetingCounts(String meeting, String year) throws Exception;

	String getMeetings(String meeting, String year) throws Exception;

	boolean checkMeeting(String meeting) throws Exception;

	boolean checkMYears(String meeting) throws  Exception;
}
