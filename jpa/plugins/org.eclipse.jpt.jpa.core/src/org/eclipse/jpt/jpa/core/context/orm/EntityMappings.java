/*******************************************************************************
 * Copyright (c) 2007, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context.orm;

import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAbstractType;
import org.eclipse.jpt.common.core.resource.java.JavaResourceAnnotatedElement;
import org.eclipse.jpt.common.utility.iterable.ListIterable;
import org.eclipse.jpt.jpa.core.context.SpecifiedAccessReference;
import org.eclipse.jpt.jpa.core.context.DeleteTypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.Generator;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.PersistentType;
import org.eclipse.jpt.jpa.core.context.Query;
import org.eclipse.jpt.jpa.core.context.TypeRefactoringParticipant;
import org.eclipse.jpt.jpa.core.context.XmlFile;
import org.eclipse.jpt.jpa.core.resource.orm.XmlEntityMappings;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

/**
 * Context model corresponding to the
 * XML resource model {@link XmlEntityMappings},
 * which corresponds to the <code>entity-mappings</code> element
 * in the <code>orm.xml</code> file.
 * <p>
 * Provisional API: This interface is part of an interim API that is still
 * under development and expected to change significantly before reaching
 * stability. It is available at this early stage to solicit feedback from
 * pioneering adopters on the understanding that any code that uses this API
 * will almost certainly be broken (repeatedly) as the API evolves.
  * 
 * @version 3.3
 * @since 2.0
*/
public interface EntityMappings
	extends MappingFile.Root, XmlFile.Root, PersistentType.Parent, SpecifiedAccessReference, DeleteTypeRefactoringParticipant, TypeRefactoringParticipant
{
	OrmXml getParent();

	OrmXml getOrmXml();

	XmlEntityMappings getXmlEntityMappings();

	String getVersion();

	String getDescription();
	void setDescription(String description);
		String DESCRIPTION_PROPERTY = "description"; //$NON-NLS-1$

	/**
	 * "The <code>package</code> subelement specifies the package of the
	 * classes listed within the subelements and attributes of the same mapping
	 * file only. The <code>package</code> subelement is overridden if the fully
	 * qualified class name is specified for a class and the two disagree."
	 * <p>
	 * Partial packages are not supported. The package element should only be used
	 * if a class is not qualified (i.e. it does not have a <code>'.'</code>)
	 * <p>
	 * <strong>NB:</strong> No mention of how to resolve duplicates in the
	 * "default" package:<ul>
	 * <li><code>Bar</code> (in "default" package) vs. <code>foo.Bar</code>
	 * </ul>
	 * when package is specified as <code>foo</code> and class is specified as
	 * <code>Bar</code>.
	 */
	String getPackage();
	void setPackage(String package_);
		String PACKAGE_PROPERTY = "package"; //$NON-NLS-1$

	/**
	 * Return the database schema container, which can be either a catalog or,
	 * if the database does not support catalogs, the database itself.
	 */
	SchemaContainer getDbSchemaContainer();

	String getSpecifiedCatalog();
	void setSpecifiedCatalog(String catalog);
		String SPECIFIED_CATALOG_PROPERTY = "specifiedCatalog"; //$NON-NLS-1$
	String getDefaultCatalog();
		String DEFAULT_CATALOG_PROPERTY = "defaultCatalog"; //$NON-NLS-1$
	Catalog getDbCatalog();

	String getSpecifiedSchema();
	void setSpecifiedSchema(String schema);
		String SPECIFIED_SCHEMA_PROPERTY = "specifiedSchema"; //$NON-NLS-1$
	String getDefaultSchema();
		String DEFAULT_SCHEMA_PROPERTY = "defaultSchema"; //$NON-NLS-1$
	Schema getDbSchema();

	/**
	 * Covariant override.
	 */
	OrmPersistenceUnitMetadata getPersistenceUnitMetadata();

	/**
	 * Covariant override.
	 */
	ListIterable<OrmManagedType> getManagedTypes();
	int getManagedTypesSize();
	OrmManagedType getManagedType(String typeName);
	void removeManagedType(int index);
	void removeManagedType(OrmManagedType managedType);
	boolean containsManagedType(String typeName);
		String MANAGED_TYPES_LIST = "managedTypes"; //$NON-NLS-1$

	/**
	 * Covariant override.
	 */
	Iterable<OrmPersistentType> getPersistentTypes();
	OrmPersistentType getPersistentType(String className);
	OrmPersistentType addPersistentType(String mappingKey, String className);

	ListIterable<OrmSequenceGenerator> getSequenceGenerators();
	int getSequenceGeneratorsSize();
	OrmSequenceGenerator addSequenceGenerator();
	OrmSequenceGenerator addSequenceGenerator(int index);
	void removeSequenceGenerator(int index);
	void removeSequenceGenerator(OrmSequenceGenerator sequenceGenerator);
	void moveSequenceGenerator(int targetIndex, int sourceIndex);
		String SEQUENCE_GENERATORS_LIST = "sequenceGenerators"; //$NON-NLS-1$

	ListIterable<OrmTableGenerator> getTableGenerators();
	int getTableGeneratorsSize();
	OrmTableGenerator addTableGenerator();
	OrmTableGenerator addTableGenerator(int index);
	void removeTableGenerator(int index);
	void removeTableGenerator(OrmTableGenerator tableGenerator);
	void moveTableGenerator(int targetIndex, int sourceIndex);
		String TABLE_GENERATORS_LIST = "tableGenerators"; //$NON-NLS-1$

	OrmQueryContainer getQueryContainer();

	/**
	 * Return all the queries defined in both the entity mappings and its
	 * entities (but <em>not</em> in any associated Java annotations).
	 */
	Iterable<Query> getMappingFileQueries();

	/**
	 * Return all the generators defined in both the entity mappings and its
	 * type mappings (but <em>not</em> in any associated Java annotations).
	 */
	Iterable<Generator> getMappingFileGenerators();

	/**
	 * Return the default package to be used for persistent types in this
	 * context.
	 */
	String getDefaultPersistentTypePackage();

	void changeMapping(OrmPersistentType ormPersistentType, OrmTypeMapping oldMapping, OrmTypeMapping newMapping);

	/**
	 * Return the Java resource type for the specified class name
	 * found in the JPA project. Prepend the default package name
	 * if the class name is not fully qualified (i.e. it does not contain a
	 * <code>'.'</code>).
	 * 
	 * @see #getPackage()
	 */
	JavaResourceAbstractType resolveJavaResourceType(String className);

	/**
	 * Return the Java resource type for the specified class name and astNodeType
	 * found in the JPA project. Prepend the default package name
	 * if the class name is not fully qualified (i.e. it does not contain a
	 * <code>'.'</code>).
	 * Return <code>null</code> if invalid or absent or if the astNodeType does not match.
	 * 
	 * @see #getPackage()
	 */
	JavaResourceAbstractType resolveJavaResourceType(String className, JavaResourceAnnotatedElement.AstNodeType astNodeType);

	/**
	 * Return the persistent type for the specified class name
	 * found in the persistence unit. Prepend the default package name
	 * if the class name is not fully qualified (i.e. it does not contain a
	 * <code>'.'</code>).
	 * 
	 * @see #getPackage()
	 */
	PersistentType resolvePersistentType(String className);

	/**
	 * Return the JDT type for the specified class name
	 * found in the Java project. Prepend the default package name
	 * if the class name is not fully qualified (i.e. it does not contain a
	 * <code>'.'</code>).
	 * 
	 * @see #getPackage()
	 */
	IType resolveJdtType(String className);

	/**
	 * If the specified class name is not qualified (i.e. it does not contain a
	 * <code>'.'</code>), prepend the default package name. Member classes must
	 * be qualified with a <code>'$'</code> for this to work correctly.
	 * 
	 * @see #getPackage()
	 */
	String qualify(String className);

	//TODO would like to remove this eventually
	void dispose();

}
