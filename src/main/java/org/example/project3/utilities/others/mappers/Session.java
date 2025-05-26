package org.example.project3.utilities.others.mappers;

import org.example.project3.beans.LoggedUserBean;
import org.example.project3.utilities.enums.Role;

public class Session {
        private LoggedUserBean user;
        private final String homepage;



        public Session() {
            this.user = null;
            this.homepage = "/com/example/project3/logic/view/homepage.fxml"; //homepage con gui
        }

        public String getHomepage(){
            return this.homepage;
        }

        public void setHomepage(Role role){
            //Assegna homepage a seconda del ruolo
        }

        public LoggedUserBean getUser(){
            return this.user;
        }

        public void setUser(LoggedUserBean user){
            this.user = user;
        }


}
