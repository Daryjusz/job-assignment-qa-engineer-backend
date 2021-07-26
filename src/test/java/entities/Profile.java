package entities;

public class Profile {

    ProfileBody profile;

    public Profile() {
    }

    public Profile(ProfileBody profile) {
        this.profile = profile;
    }

    public ProfileBody getProfile() {
        return profile;
    }

    public static class ProfileBody {
        String username;
        String bio;
        String image;
        boolean following;

        public ProfileBody() {
        }

        public ProfileBody(String image, boolean following, String bio, String username) {
            this.bio = bio;
            this.image = image;
            this.following = following;
            this.username = username;
        }

        public String getUsername() {
            return username;
        }

        public String getBio() {
            return bio;
        }

        public String getImage() {
            return image;
        }

        public boolean isFollowing() {
            return following;
        }
    }
}
