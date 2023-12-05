    package easydiner.API.Enum;

    public enum Role {
        USER, ADMIN, OWNER;
        public String getAuthority() {
            return "ROLE_" + this.name();
        }

    }
