/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
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
package org.eclipse.jpt.jpa.core.jpql;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import org.eclipse.jdt.core.IClassFile;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.utility.internal.StringTools;
import org.eclipse.jpt.common.utility.internal.iterable.FilteringIterable;
import org.eclipse.jpt.common.utility.internal.iterable.TransformationIterable;
import org.eclipse.jpt.jpa.core.JpaProject;
import org.eclipse.jpt.jpa.db.Database;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.Table;
import org.eclipse.persistence.jpa.jpql.ContentAssistExtension;
import org.eclipse.persistence.jpa.jpql.ContentAssistProposals.ClassType;
import org.eclipse.persistence.jpa.jpql.ExpressionTools;

/**
 * This extension can be used to provide additional support to JPQL content assist that is outside
 * the scope of providing proposals related to JPA metadata. It adds support for providing
 * suggestions related to class names, enum constants, table names, column names.
 * <p>
 * Note: This will only be invoked if the JPQL grammar supports it, generic JPA does not.
 *
 * @version 3.3
 * @since 3.3
 * @author Pascal Filion
 */
public class DefaultContentAssistExtension implements ContentAssistExtension {

	/**
	 * The JPA project is necessary to give access to the additional information.
	 */
	private JpaProject jpaProject;

	/**
	 * The character used to quote a table name, which is used to case-sensitive or containing
	 * special characters.
	 */
	private static final char TABLE_QUALIFIER = '`';

	/**
	 * Creates a new <code>DefaultContentAssistExtension</code>.
	 *
	 * @param jpaProject The JPA project is necessary to give access to the additional information
	 */
	public DefaultContentAssistExtension(JpaProject jpaProject) {
		super();
		this.jpaProject = jpaProject;
	}

	protected boolean acceptClass(IType type,
	                              String className,
	                              ClassType classType,
	                              String prefix,
	                              boolean hasDot) throws Exception {

		// Filter first on the name because it's a lot less expensive
		// than asking question on IType
		if (!isClassFiltered(className, prefix, hasDot)) {
			return !type.isAnonymous() &&
			       (classType == ClassType.INSTANTIABLE) ? type.isClass() : type.isEnum() &&
			       !type.isMember();
		}

		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<String> classNames(String prefix, ClassType classType) {

		IJavaProject javaProject = jpaProject.getJavaProject();
		Set<String> classNames = new HashSet<String>();
		boolean emptyPrefix = (prefix.length() == 0);
		int index = !emptyPrefix ? prefix.lastIndexOf('.') : -1;
		boolean hasDot = (index > -1);

		// Note: In order to increase performance by preventing array creation and filtering,
		//       IParent.getChildren() is used, then a simple check is performed on the element type
		try {

			// Traverse the packages
			for (IPackageFragment packageFragment : javaProject.getPackageFragments()) {
				String packageName = packageFragment.getElementName();

				// Filter the package out if the prefix has a dot,
				// match the beginning of the prefix up to the last dot
				boolean packageAccepted = !hasDot || packageName.regionMatches(true, 0, prefix, 0, index);

				if (packageAccepted) {

					// Traverse the classes within the package
					for (IJavaElement javaElement : packageFragment.getChildren()) {

						switch (javaElement.getElementType()) {

							// Java source files
							case IJavaElement.COMPILATION_UNIT: {

								ICompilationUnit compilationUnit = (ICompilationUnit) javaElement;

								for (IJavaElement cuChild : compilationUnit.getChildren()) {

									// Class definition
									if (cuChild.getElementType() == IJavaElement.TYPE) {
										IType type = (IType) cuChild;
										String className = type.getFullyQualifiedName();

										// Filter out the class if required
										// NOTE: An empty prefix will not filter anonymous or member classes,
										//       to do so, we need to use what the Java content assist uses
										//       otherwise it takes too long to calculate the valid classes
										if (emptyPrefix || acceptClass(type, className, classType, prefix, hasDot)) {
											classNames.add(className);
										}
									}
								}

								break;
							}

							// Java class files
							case IJavaElement.CLASS_FILE: {

								IClassFile classFile = (IClassFile) javaElement;
								IType type = classFile.getType();
								String className = type.getFullyQualifiedName();

								// Filter out the class if required
								// NOTE: An empty prefix will not filter anonymous or member classes,
								//       to do so, we need to use what the Java content assist uses
								//       otherwise it takes too long to calculate the valid classes
								if (emptyPrefix || acceptClass(type, className, classType, prefix, hasDot)) {
									classNames.add(className);
								}

								break;
							}
						}
					}
				}
			}
		}
		catch (Exception e) {
			// Just ignore
		}

		return classNames;
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<String> columnNames(String tableName, final String prefix) {

		Schema schema = getSchema();

		if (schema == null) {
			return Collections.emptyList();
		}

		Table table = schema.getTableNamed(tableName);

		if (table == null) {
			return Collections.emptyList();
		}

		if (prefix.length() == 0) {
			return table.getSortedColumnIdentifiers();
		}

		return new FilteringIterable<String>(table.getSortedColumnIdentifiers()) {
			@Override
			protected boolean accept(String name) {
				return ExpressionTools.startWithIgnoreCase(name, prefix);
			}
		};
	}

	protected Schema getSchema() {
		Database database = jpaProject.getDataSource().getDatabase();
		return (database == null) ? null : database.getDefaultSchema();
	}

	protected boolean isClassFiltered(String className, String prefix, boolean hasDot) {

		// The prefix does not have a dot, it's not a package name, retrieve the short class name
		if (!hasDot) {
			int index = className.lastIndexOf('.');
			if (index > -1) {
				className = className.substring(index + 1, className.length());
			}
		}

		if (className.lastIndexOf('$') > -1) {
			return true;
		}

		// Filter using the prefix
		return !StringTools.startsWithIgnoreCase(className, prefix);
	}

	protected Iterable<String> tableNames(Schema schema) {
		return new TransformationIterable<String, String>(schema.getSortedTableIdentifiers()) {
			@Override
			protected String transform(String tableName) {
				if (tableName.charAt(0) == TABLE_QUALIFIER) {
					tableName = StringTools.undelimit(tableName, 1);
				}
				return tableName;
			}
		};
	}

	/**
	 * {@inheritDoc}
	 */
	public Iterable<String> tableNames(final String prefix) {

		Schema schema = getSchema();

		if (schema == null) {
			return Collections.emptyList();
		}

		return new FilteringIterable<String>(tableNames(schema)) {
			@Override
			protected boolean accept(String tableName) {
				return StringTools.startsWithIgnoreCase(tableName, prefix);
			}
		};
	}
}