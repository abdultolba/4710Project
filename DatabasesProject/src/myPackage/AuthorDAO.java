package myPackage;

public interface AuthorDAO {
	AuthorDTO getAuthorByName ( );

	AuthorDTO getAuthorOfPaper ( );

	boolean insertAuthor ( );

	boolean updateAuthor ( );

	boolean deleteAuthor ( );
}
