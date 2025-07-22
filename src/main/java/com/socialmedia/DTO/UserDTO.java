package com.socialmedia.DTO;

import com.socialmedia.model.User;

public class UserDTO {
	private Long id;
	private String username;
	private String profilePicUrl;

    public UserDTO(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.profilePicUrl = user.getProfilePicUrl();
    }
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }
	/*public boolean isFollowing() {
		return following;
	}
	public void setFollowing(boolean following) {
		this.following = following;
	}*/
}