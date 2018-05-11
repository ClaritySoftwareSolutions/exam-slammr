
class UserProfileFactory {

    /**
     * Returns a UserProfile instance from a Facebook public profile
     *
     * @param socialPublicProfile
     */
    static fromFacebookResponse(socialPublicProfile) {
        return {
            'id': socialPublicProfile.id,
            'name': socialPublicProfile.name,
            'email': socialPublicProfile.email,
            'profilePicture': {
                'height': socialPublicProfile.picture.data.height,
                'width': socialPublicProfile.picture.data.width,
                'url': socialPublicProfile.picture.data.url
            }
        }
    }
}

export default UserProfileFactory;