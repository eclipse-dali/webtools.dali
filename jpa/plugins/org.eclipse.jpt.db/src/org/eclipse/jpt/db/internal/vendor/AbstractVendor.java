/*******************************************************************************
 * Copyright (c) 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.db.internal.vendor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.StringTools;

/**
 * Consolidate the behavior common to the typical vendors.
 */
abstract class AbstractVendor
	implements Vendor
{

	AbstractVendor() {
		super();
	}

	public abstract String getDTPVendorName();


	// ********** catalog and schema support **********

	abstract CatalogStrategy getCatalogStrategy();

	public boolean supportsCatalogs(Database database) {
		return this.getCatalogStrategy().supportsCatalogs(database);
	}

	public List<Catalog> getCatalogs(Database database) {
		return this.getCatalogStrategy().getCatalogs(database);
	}

	public List<Schema> getSchemas(Database database) {
		try {
			return this.getCatalogStrategy().getSchemas(database);
		} catch (Exception ex) {
			throw new RuntimeException("vendor: " + this, ex); //$NON-NLS-1$
		}
	}

	/**
	 * Typically, the name of the default catalog is the user name.
	 */
	public final List<String> getDefaultCatalogIdentifiers(Database database, String userName) {
		ArrayList<String> identifiers = new ArrayList<String>();
		this.addDefaultCatalogIdentifiersTo(database, userName, identifiers);
		return identifiers;
	}

	void addDefaultCatalogIdentifiersTo(@SuppressWarnings("unused") Database database, String userName, ArrayList<String> identifiers) {
		identifiers.add(this.convertNameToIdentifier(userName));
	}

	/**
	 * Typically, the name of the default schema is the user name.
	 */
	public final List<String> getDefaultSchemaIdentifiers(Database database, String userName) {
		ArrayList<String> identifiers = new ArrayList<String>();
		this.addDefaultSchemaIdentifiersTo(database, userName, identifiers);
		return identifiers;
	}

	void addDefaultSchemaIdentifiersTo(@SuppressWarnings("unused") Database database, String userName, ArrayList<String> identifiers) {
		identifiers.add(this.convertNameToIdentifier(userName));
	}


	// ********** folding strategy used to convert names and identifiers **********

	/**
	 * The SQL spec says a "normal" (non-delimited) identifier should be
	 * folded to uppercase; but some databases do otherwise (e.g. Sybase).
	 */
	abstract FoldingStrategy getFoldingStrategy();


	// ********** name -> identifier **********

	public String convertNameToIdentifier(String name, String defaultName) {
		return this.nameRequiresDelimiters(name) ? this.delimitName(name)
						: this.normalNamesMatch(name, defaultName) ? null : name;
	}

	public String convertNameToIdentifier(String name) {
		return this.nameRequiresDelimiters(name) ? this.delimitName(name) : name;
	}

	/**
	 * Return whether the specified database object name must be delimited
	 * when used in an SQL statement.
	 * If the name has any "special" characters (as opposed to letters,
	 * digits, and other allowed characters [e.g. underscores]), it requires
	 * delimiters.
	 * If the name is mixed case and the database folds undelimited names
	 * (to either uppercase or lowercase), it requires delimiters.
	 */
	boolean nameRequiresDelimiters(String name) {
		return (name.length() == 0)  //  an empty string must be delimited(?)
					|| this.nameContainsAnyNonNormalCharacters(name)
					|| this.nameIsNotFolded(name);
	}

	/**
	 * Return whether the specified name contains any "non-normal" characters
	 * that require the name to be delimited.
	 * Pre-condition: the specified name is not empty
	 */
	boolean nameContainsAnyNonNormalCharacters(String name) {
		char[] string = name.toCharArray();
		if (this.characterIsNonNormalNameStart(string[0])) {
			return true;
		}
		for (int i = string.length; i-- > 1; ) {  // note: stop at 1
			if (this.characterIsNonNormalNamePart(string[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified character is "non-normal" for the first
	 * character of a name.
	 * Typically, databases are more restrictive about what characters can
	 * be used to *start* an identifier (as opposed to the characters
	 * allowed for the remainder of the identifier).
	 */
	boolean characterIsNonNormalNameStart(char c) {
		return ! this.characterIsNormalNameStart(c);
	}

	/**
	 * Return whether the specified character is "normal" for the first
	 * character of a name.
	 * The first character of an identifier can be:
	 *   - a letter
	 *   - any of the extended, vendor-specific, "normal" start characters
	 */
	boolean characterIsNormalNameStart(char c) {
		// all vendors allow a letter
		return Character.isLetter(c)
				|| this.characterIsExtendedNormalNameStart(c);
	}

	boolean characterIsExtendedNormalNameStart(char c) {
		return arrayContains(this.getExtendedNormalNameStartCharacters(), c);
	}

	/**
	 * Return the "normal" characters, beyond letters, for the
	 * first character of a name.
	 * Return null if there are no "extended" characters.
	 */
	char[] getExtendedNormalNameStartCharacters() {
		return null;
	}

	/**
	 * Return whether the specified character is "non-normal" for the second
	 * and subsequent characters of a name.
	 */
	boolean characterIsNonNormalNamePart(char c) {
		return ! this.characterIsNormalNamePart(c);
	}

	/**
	 * Return whether the specified character is "normal" for the second and
	 * subsequent characters of a name.
	 * The second and subsequent characters of a "normal" name can be:
	 *   - a letter
	 *   - a digit
	 *   - any of the extended, vendor-specific, "normal" start characters
	 *   - any of the extended, vendor-specific, "normal" part characters
	 */
	boolean characterIsNormalNamePart(char c) {
		// all vendors allow a letter or digit
		return Character.isLetterOrDigit(c)
				|| this.characterIsExtendedNormalNameStart(c)
				|| this.characterIsExtendedNormalNamePart(c);
	}

	boolean characterIsExtendedNormalNamePart(char c) {
		return arrayContains(this.getExtendedNormalNamePartCharacters(), c);
	}

	/**
	 * Return the "normal" characters, beyond letters and digits and the
	 * "normal" first characters, for the second and subsequent characters
	 * of an identifier. Return null if there are no additional characters.
	 */
	char[] getExtendedNormalNamePartCharacters() {
		return null;
	}

	/**
	 * Return whether the specified name is not folded to the database's
	 * case, requiring it to be delimited.
	 */
	boolean nameIsNotFolded(String name) {
		return ! this.getFoldingStrategy().nameIsFolded(name);
	}

	/**
	 * Return whether the specified "normal" names match.
	 */
	boolean normalNamesMatch(String name1, String name2) {
		return this.normalIdentifiersAreCaseSensitive() ?
						name1.equals(name2)
					:
						name1.equalsIgnoreCase(name2);
	}

	/**
	 * Typically, "normal" identifiers are case-insensitive.
	 */
	boolean normalIdentifiersAreCaseSensitive() {
		return this.getFoldingStrategy().normalIdentifiersAreCaseSensitive();
	}

	/**
	 * Wrap the specified name in delimiters (typically double-quotes),
	 * converting it to an identifier.
	 */
	String delimitName(String name) {
		return StringTools.quote(name);
	}


	// ********** identifier -> name **********

	// not sure how to handle an empty string:
	// both "" and "\"\"" are converted to "" ...
	// convert "" to 'null' since empty strings must be delimited?
	public String convertIdentifierToName(String identifier) {
		return (identifier == null) ? null :
					this.identifierIsDelimited(identifier) ?
						StringTools.undelimit(identifier)
					:
						this.getFoldingStrategy().fold(identifier);
	}

	/**
	 * Return whether the specified identifier is "delimited".
	 * The SQL-92 spec says identifiers should be delimited by
	 * double-quotes; but some databases allow otherwise (e.g. Sybase).
	 */
	boolean identifierIsDelimited(String identifier) {
		return StringTools.stringIsQuoted(identifier);
	}


	// ********** misc **********

	@Override
	public String toString() {
		return this.getDTPVendorName();
	}

	/**
	 * static convenience method - array null check
	 */
	static boolean arrayContains(char[] array, char c) {
		return (array != null) && CollectionTools.contains(array, c);
	}

}
