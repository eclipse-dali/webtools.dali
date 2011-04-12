/*******************************************************************************
 * Copyright (c) 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.ui.internal.jpql;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import org.eclipse.osgi.util.NLS;
import org.eclipse.persistence.jpa.internal.jpql.parser.Expression;

/**
 * The localized messages describing the JPQL identifiers.
 *
 * @version 3.0
 * @since 3.0
 * @author Pascal Filion
 */
@SuppressWarnings({"nls", "restriction"})
final class JpqlIdentifierMessages extends NLS {

	// JPA 1.0 identifiers
	public static String ABS;
	public static String ALL;
	public static String ANY;
	public static String ASC;
	public static String AVG;
	public static String BETWEEN;
	public static String BOTH;
	public static String CONCAT;
	public static String COUNT;
	public static String CURRENT_DATE;
	public static String CURRENT_TIME;
	public static String CURRENT_TIMESTAMP;
	public static String DELETE_FROM;
	public static String DESC;
	public static String DISTINCT;
	public static String ESCAPE;
	public static String EXISTS;
	public static String FROM;
	public static String GROUP_BY;
	public static String HAVING;
	public static String IN;
	public static String IS_EMPTY;
	public static String IS_NULL;
	public static String JOIN;
	public static String JOIN_FETCH;
	public static String LEADING;
	public static String LENGTH;
	public static String LIKE;
	public static String LOCATE;
	public static String LOWER;
	public static String MAX;
	public static String MEMBER;
	public static String MIN;
	public static String MOD;
	public static String NEW;
	public static String OBJECT;
	public static String ORDER_BY;
	public static String SELECT;
	public static String SIZE;
	public static String SOME;
	public static String SQRT;
	public static String SUBSTRING;
	public static String SUM;
	public static String TRAILING;
	public static String TRIM;
	public static String UPDATE;
	public static String UPPER;
	public static String WHERE;

	// JPA 2.0 identifiers
	public static String CASE;
	public static String COALESCE;
	public static String ENTRY;
	public static String INDEX;
	public static String KEY;
	public static String NULLIF;
	public static String TYPE;
	public static String VALUE;

	// EclipseLink identifiers
	public static String FUNC;
	public static String TREAT;

	// Reserved JPQL identifiers
	public static String BIT_LENGTH;
	public static String CHAR_LENGTH;
	public static String CHARACTER_LENGTH;
	public static String CLASS;
	public static String POSITION;
	public static String UNKNOWN;

	static {
		NLS.initializeMessages("jpt_ui_jpql_identifiers", JpqlIdentifierMessages.class);
	}

	JpqlIdentifierMessages() {
		super();
		throw new UnsupportedOperationException();
	}

	private static Map<String, String> registeredIdentifers;

	private static Map<String, String> buildRegisteredIdentifers() {

		Map<String, String> identifiers = new HashMap<String, String>();

		identifiers.put(Expression.IS_NOT_EMPTY,  Expression.IS_EMPTY);
		identifiers.put(Expression.IS_NOT_NULL,   Expression.IS_NULL);
		identifiers.put(Expression.MEMBER_OF,     Expression.MEMBER);
		identifiers.put(Expression.NOT_IN,        Expression.IN);
		identifiers.put(Expression.NOT_BETWEEN,   Expression.BETWEEN);
		identifiers.put(Expression.NOT_MEMBER,    Expression.MEMBER);
		identifiers.put(Expression.NOT_MEMBER_OF, Expression.MEMBER);

		identifiers.put(Expression.INNER_JOIN,      Expression.JOIN);
		identifiers.put(Expression.LEFT_JOIN,       Expression.JOIN);
		identifiers.put(Expression.LEFT_OUTER_JOIN, Expression.JOIN);

		identifiers.put(Expression.INNER_JOIN_FETCH,      Expression.JOIN_FETCH);
		identifiers.put(Expression.LEFT_JOIN_FETCH,       Expression.JOIN_FETCH);
		identifiers.put(Expression.LEFT_OUTER_JOIN_FETCH, Expression.JOIN_FETCH);

		return identifiers;
	}

	/**
	 * Converts the given JPQL identifier to the shortest form that is used to map the localized
	 * message. If the identifier is <b>NOT MEMBER</b> for instance, then it needs to be converted
	 * to <b>MEMBER</b> since that's the identifier used to map the localize message.
	 *
	 * @param identifier The JPQL identifier to convert, if needed
	 * @return Either the given JPQL identifier or its counterpart that is used to map the localized
	 * message
	 */
	private static String convertIdentifier(String identifier) {
		identifier = registeredIdentifer(identifier);
		identifier = identifier.replace(" ", "_");
		return identifier;
	}

	/**
	 * Retrieves the localized message for the given string value, if it's a JPQL identifier.
	 *
	 * @param identifier The JPQL identifier for which its localized description is needed
	 * @return The localized message describing the given JPQL identifier or <code>null</code> if the
	 * given choice is not an identifier
	 */
	static String localizedMessage(String identifier) {
		try {
			identifier = convertIdentifier(identifier);
			Field constant = JpqlIdentifierMessages.class.getField(identifier);
			return (String) constant.get(null);
		}
		catch (Exception e) {
			// The choice is not a JPQL identifier
			return null;
		}
	}

	private static String registeredIdentifer(String identifier) {
		String another = registeredIdentifers().get(identifier);
		if (another != null) {
			return another;
		}
		return identifier;
	}

	private static Map<String, String> registeredIdentifers() {
		if (registeredIdentifers == null) {
			registeredIdentifers = buildRegisteredIdentifers();
		}
		return registeredIdentifers;
	}
}