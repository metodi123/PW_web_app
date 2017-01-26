package pw.web.app.model;

public enum UserType {

	Admin;
	
	@Override
	public String toString(){
        switch(this){
        case Admin :
            return "admin";
		}
        return null;
    }
}