/*******************************************************************************
 * Copyright (c) 2009, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.db.internal.vendor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.datatools.modelbase.sql.schema.Catalog;
import org.eclipse.datatools.modelbase.sql.schema.Database;
import org.eclipse.datatools.modelbase.sql.schema.Schema;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.StringTools;

/**
 * Consolidate the behavior common to the typical vendors.
 * 
 * @see UnrecognizedVendor
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
	public final Iterable<String> getDefaultCatalogNames(Database database, String userName) {
		if ( ! this.supportsCatalogs(database)) {
			return Collections.emptyList();
		}
		ArrayList<String> names = new ArrayList<String>();
		this.addDefaultCatalogNamesTo(database, userName, names);
		return names;
	}

	/**
	 * See comment at
	 * {@link #addDefaultSchemaNamesTo(Database, String, ArrayList)}.
	 */
	void addDefaultCatalogNamesTo(@SuppressWarnings("unused") Database database, String userName, ArrayList<String> names) {
		names.add(this.convertIdentifierToName(userName));
	}

	/**
	 * Typically, the name of the default schema is the user name.
	 */
	public final Iterable<String> getDefaultSchemaNames(Database database, String userName) {
		ArrayList<String> names = new ArrayList<String>();
		this.addDefaultSchemaNamesTo(database, userName, names);
		return names;
	}

	/**
	 * The user name passed in here was retrieved from DTP.
	 * DTP stores the user name that was passed to it during the connection
	 * to the database. As a result, this user name is an <em>identifier</em>
	 * not a <em>name</em>.
	 * If the user name were retrieved from the JDBC connection it would probably
	 * be a <em>name</em>. For example, you can connect to an Oracle database with the
	 * user name "scott", but that identifer is folded to the actual user name
	 * "SCOTT". DTP stores the original string "scott", while the Oracle JDBC
	 * driver stores the folded string "SCOTT".
	 */
	void addDefaultSchemaNamesTo(@SuppressWarnings("unused") Database database, String userName, ArrayList<String> names) {
		names.add(this.convertIdentifierToName(userName));
	}


	// ********** folding strategy used to convert names and identifiers **********

	/**
	 * The SQL spec says a <em>regular</em> (non-delimited) identifier should be
	 * folded to uppercase; but some databases do otherwise (e.g. Sybase).
	 */
	abstract FoldingStrategy getFoldingStrategy();


	// ********** name -> identifier **********

	public String convertNameToIdentifier(String name, String defaultName) {
		return this.nameRequiresDelimiters(name) ?
						this.delimitName(name) :
						this.regularNamesMatch(name, defaultName) ? null : name;
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
					|| this.nameContainsAnySpecialCharacters(name)
					|| this.nameIsNotFolded(name);
	}

	/**
	 * Return whether the specified name contains any "special" characters
	 * that require the name to be delimited.
	 * Pre-condition: the specified name is not empty
	 */
	boolean nameContainsAnySpecialCharacters(String name) {
		char[] string = name.toCharArray();
		if (this.characterIsNonRegularNameStart(string[0])) {
			return true;
		}
		for (int i = string.length; i-- > 1; ) {  // note: stop at 1
			if (this.characterIsNonRegularNamePart(string[i])) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return whether the specified character is "non-regular" for the first
	 * character of a name.
	 * Typically, databases are more restrictive about what characters can
	 * be used to <em>start</em> an identifier (as opposed to the characters
	 * allowed for the remainder of the identifier).
	 */
	boolean characterIsNonRegularNameStart(char c) {
		return ! this.characterIsRegularNameStart(c);
	}

	/**
	 * Return whether the specified character is <em>regular</em> for the first
	 * character of a name.
	 * The first character of an identifier can be:<ul>
	 * <li>a letter
	 * <li>any of the extended, vendor-specific, <em>regular</em> start characters
	 * </ul>
	 */
	boolean characterIsRegularNameStart(char c) {
		// all vendors allow a letter
		return Character.isLetter(c)
				|| this.characterIsExtendedRegularNameStart(c);
	}

	boolean characterIsExtendedRegularNameStart(char c) {
		return arrayContains(this.getExtendedRegularNameStartCharacters(), c);
	}

	/**
	 * Return the <em>regular</em> characters, beyond letters, for the
	 * first character of a name.
	 * Return null if there are no "extended" characters.
	 */
	char[] getExtendedRegularNameStartCharacters() {
		return null;
	}

	/**
	 * Return whether the specified character is "non-regular" for the second
	 * and subsequent characters of a name.
	 */
	boolean characterIsNonRegularNamePart(char c) {
		return ! this.characterIsRegularNamePart(c);
	}

	/**
	 * Return whether the specified character is <em>regular</em> for the second and
	 * subsequent characters of a name.
	 * The second and subsequent characters of a <em>regular</em> name can be:<ul>
	 * <li>a letter
	 * <li>a digit
	 * <li>an underscore
	 * <li>any of the extended, vendor-specific, <em>regular</em> start characters
	 * <li>any of the extended, vendor-specific, <em>regular</em> part characters
	 * </ul>
	 */
	boolean characterIsRegularNamePart(char c) {
		// all vendors allow a letter or digit
		return Character.isLetterOrDigit(c) ||
				(c == '_') ||
				this.characterIsExtendedRegularNameStart(c) ||
				this.characterIsExtendedRegularNamePart(c);
	}

	boolean characterIsExtendedRegularNamePart(char c) {
		return arrayContains(this.getExtendedRegularNamePartCharacters(), c);
	}

	/**
	 * Return the <em>regular</em> characters, beyond letters and digits and the
	 * <em>regular</em> first characters, for the second and subsequent characters
	 * of an identifier. Return <code>null</code> if there are no additional characters.
	 */
	char[] getExtendedRegularNamePartCharacters() {
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
	 * Return whether the specified <em>regular</em> names match.
	 */
	boolean regularNamesMatch(String name1, String name2) {
		return this.regularIdentifiersAreCaseSensitive() ?
						name1.equals(name2) :
						name1.equalsIgnoreCase(name2);
	}

	/**
	 * Typically, <em>regular</em> identifiers are case-insensitive.
	 */
	boolean regularIdentifiersAreCaseSensitive() {
		return this.getFoldingStrategy().regularIdentifiersAreCaseSensitive();
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
		return (identifier == null) ?
					null :
					this.identifierIsDelimited(identifier) ?
							StringTools.undelimit(identifier) :
							this.getFoldingStrategy().fold(identifier);
	}

	/**
	 * Return whether the specified identifier is <em>delimited</em>.
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
		return (array != null) && ArrayTools.contains(array, c);
	}

}
