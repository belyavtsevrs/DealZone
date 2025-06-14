# DealZone

## Functional requirements

### User registration and authentication:
- Users must be able to register and login using email and password
- The system must validate the uniqiuness of the email 
- Users must be able to log out of the account
### User Profile:
- Users must be able to upload and update a profile avatar
- Users must be able to edit their profile information
- Users must be able change their password
- Users must be able to view : their product list, favoirite products
### Product management
- Authenticated users must be able to create a product 
- Unauthenticated users must be able to view product list
- Users must be able to mark their product as sold or delete it
- Users must be able to filter products by: category , price , region ,date added
- Users must be able to search for products by title
## Unfunctional requirements

### Performance
- The system's response shouldn't exceed 500 ms
- Thy system must proccess at least 1000 request per minute without performance decreasing
### Security
- User passwords must be stored in encrypted form
- Authentication and authorization must work correctly to prevent unauthorized access
### Usability
- The interface should be intuitive and user-friendly for users 
- Error messages should be clear and informative