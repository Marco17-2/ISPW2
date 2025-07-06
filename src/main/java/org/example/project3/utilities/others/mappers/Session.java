package org.example.project3.utilities.others.mappers;

import org.example.project3.beans.LoggedUserBean;
import org.example.project3.utilities.enums.Role;

public class Session {
        private LoggedUserBean user;

        public Session() {
            this.user = null;
        }

        public LoggedUserBean getUser(){
            return this.user;
        }

        public void setUser(LoggedUserBean user){
            this.user = user;
        }

}
