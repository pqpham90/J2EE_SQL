package assign.services;

public interface MyEavesDropService {
	int getMeetings(String meeting, String year) throws Exception;

	boolean checkMeeting(String meeting) throws Exception;

	boolean checkMYears(String meeting) throws  Exception;
}
