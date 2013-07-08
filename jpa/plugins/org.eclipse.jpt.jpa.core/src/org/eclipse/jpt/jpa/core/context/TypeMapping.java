/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.context;

import org.eclipse.jpt.common.core.resource.java.JavaResourceType;
import org.eclipse.jpt.common.utility.internal.ArrayTools;
import org.eclipse.jpt.common.utility.internal.predicate.PredicateAdapter;
import org.eclipse.jpt.common.utility.internal.transformer.TransformerAdapter;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.db.Schema;

/**
 * type mapping:<ul>
 * <li>entity
 * <li>mapped superclass
 * <li>embeddable
 * </ul>
 * Provisional API: This interface is part of an interim API that is still under
 * development and expected to change significantly before reaching stability.
 * It is available at this early stage to solicit feedback from pioneering
 * adopters on the understanding that any code that uses this API will almost
 * certainly be broken (repeatedly) as the API evolves.
 * 
 * @version 3.6
 * @since 2.0
 */
public interface TypeMapping
		extends JpaContextModel {
	
	PersistentType getPersistentType();
	
	Transformer<TypeMapping, PersistentType> PERSISTENT_TYPE_TRANSFORMER = new PersistentTypeTransformer();
	
	class PersistentTypeTransformer
			extends TransformerAdapter<TypeMapping, PersistentType> {
		@Override
		public PersistentType transform(TypeMapping tm) {
			return tm.getPersistentType();
		}
	}
	
	/**
	 * Return the corresponding java resource type, this can be null
	 */
	JavaResourceType getJavaResourceType();


	/**
	 * Return a unique key for the type mapping. If this is defined in an
	 * extension they should be equal.
	 */
	String getKey();

	/**
	 * Return the name, specified or default if not specified.
	 */
	String getName();

	boolean isMapped();
	
	Iterable<Query> getQueries();
	Transformer<TypeMapping, Iterable<Query>> QUERIES_TRANSFORMER = new QueriesTransformer();
	class QueriesTransformer
		extends TransformerAdapter<TypeMapping, Iterable<Query>>
	{
		@Override
		public Iterable<Query> transform(TypeMapping mapping) {
			return mapping.getQueries();
		}
	}

	Iterable<Generator> getGenerators();
	Transformer<TypeMapping, Iterable<Generator>> GENERATORS_TRANSFORMER = new GeneratorsTransformer();
	class GeneratorsTransformer
		extends TransformerAdapter<TypeMapping, Iterable<Generator>>
	{
		@Override
		public Iterable<Generator> transform(TypeMapping mapping) {
			return mapping.getGenerators();
		}
	}
	
	
	// ********** inheritance **********

	/**
	 * String associated with changes to the super type mapping property
	 */
	String SUPER_TYPE_MAPPING_PROPERTY = "superTypeMapping"; //$NON-NLS-1$
	
	/**
	 * Return the "super" {@link TypeMapping} from the "persistence" 
	 * inheritance hierarchy.
	 * If the Java inheritance parent is not persistent, then continue
	 * up the hierarchy (the JPA spec allows non-persistent types to be 
	 * part of the hierarchy.)
	 * Return <code>null</code> if the type mapping is the root.
	 * <p>
	 * Example:
	 * <pre>
	 * &#64;Entity
	 * public abstract class Model {}
	 * 
	 * public abstract class Animal extends Model {}
	 * 
	 * &#64;Entity
	 * public class Cat extends Animal {}
	 * </pre>
	 * The "super" type mapping of the <code>Cat</code> type mapping is
	 * the <code>Model</code> type mapping. The "super" type mapping can
	 * either come from a Java annotated class from an XML mapping file.
	 */
	IdTypeMapping getSuperTypeMapping();
	
	/**
	 * Return the type mapping's "persistence" inheritance hierarchy,
	 * <em>excluding</em> the type mapping itself.
	 * If there is an inheritance loop, the iterable will terminate before including
	 * this type mapping.
	 */
	Iterable<IdTypeMapping> getAncestors();
	
	/**
	 * Return the type mapping's "persistence" inheritance hierarchy,
	 * <em>including</em> the type mapping itself.
	 * If there is an inheritance loop, the iterable will terminate before including
	 * this type mapping.
	 */
	Iterable<? extends TypeMapping> getInheritanceHierarchy();
	
	
	// ********** tables **********

	/**
	 * Return the type mapping's primary table name.
	 * Return null if a primary table is not applicable.
	 */
	String getPrimaryTableName();

	/**
	 * Return the type mapping's primary database table.
	 * Return null if a primary table is not applicable.
	 */
	org.eclipse.jpt.jpa.db.Table getPrimaryDbTable();

	Schema getDbSchema();

	/**
	 * Return the type mapping's "associated" tables, which includes the primary
	 * table and the collection of secondary tables.
	 */
	Iterable<Table> getAssociatedTables();

	/**
	 * Return the type mapping's "associated" tables, which includes the primary
	 * table and the collection of secondary tables, as well as all inherited
	 * "associated" tables.
	 */
	Iterable<Table> getAllAssociatedTables();

	/**
	 * Return the names of the type mapping's "associated" tables, which
	 * includes the primary table and the collection of secondary tables, as
	 * well as all the inherited "associated" tables.
	 */
	Iterable<String> getAllAssociatedTableNames();

	/**
	 * Return the resolved <em>associated</em> db table with the specified name;
	 * i.e. the specified table must be among those associated with the type
	 * mapping otherwise return <code>null</code>.
	 * <p>
	 * <strong>NB:</strong> The spec does allow for columns or join columns to
	 * specify a schema and/or catalog; so the results can be unpredictable....
	 */
	org.eclipse.jpt.jpa.db.Table resolveDbTable(String tableName);

	/**
	 * Return whether the specified table is invalid for any annotations
	 * associated with the type mapping. The table name is case-
	 * <em>sensitive</em> when the database connection is missing or inactive;
	 * when the database connection is active, case-sensitivity is determined
	 * by the database.
	 */
	boolean tableNameIsInvalid(String tableName);
	
	
	// ********** attribute mappings **********
	
	/**
	 * A convenience method for getting the attribute mappings from PersistentType.attributes()
	 */
	<T extends AttributeMapping> Iterable<T> getAttributeMappings();
	
	/**
	 * Return attribute mappings of a particular mapping type that are declared on this type mapping
	 */
	<T extends AttributeMapping> Iterable<T> getAttributeMappings(String mappingKey);
	
	/**
	 * Return all the attribute mappings in the type mapping's
	 * inheritance hierarchy.
	 */
	Iterable<AttributeMapping> getAllAttributeMappings();
	
	/**
	 * Return all the non-transient attribute mappings in the type mapping's
	 * inheritance hierarchy
	 */
	Iterable<AttributeMapping> getNonTransientAttributeMappings();

	/**
	 * Return attribute mappings of a particular mapping type that are declared anywhere on this 
	 * type mapping's hierarchy
	 */
	<T extends AttributeMapping> Iterable<T> getAllAttributeMappings(String mappingKey);
	
	/**
	 * Return whether the given attribute mapping key is valid for this
	 * particular type mapping (for example, id's are not valid for an
	 * embeddable type mapping)
	 */
	boolean attributeMappingKeyAllowed(String attributeMappingKey);
	
	/**
	 * Return all mappings that are mapped as id mappings
	 * @see MappingIsIdMapping
	 */
	Iterable<AttributeMapping> getIdAttributeMappings();
	
	/**
	 * Return the (single) mapping used as the id mapping for this type mapping.
	 * If there is more than one, return null.
	 * @see MappingIsIdMapping
	 */
	AttributeMapping getIdAttributeMapping();
	
	static String[] ID_ATTRIBUTE_MAPPING_KEYS 
			= new String[] { MappingKeys.ID_ATTRIBUTE_MAPPING_KEY, MappingKeys.EMBEDDED_ID_ATTRIBUTE_MAPPING_KEY };
	
	static class MappingIsIdMapping
			extends PredicateAdapter<AttributeMapping> {
		@Override
		public boolean evaluate(AttributeMapping mapping) {
			return ArrayTools.contains(ID_ATTRIBUTE_MAPPING_KEYS, mapping.getKey());
		}
	}
	
	/**
	 * Return whether the attribute with the specified name is a derived ID.
	 */
	boolean attributeIsDerivedId(String attributeName);


	// ********** attribute overrides **********

	/**
	 * Return an Iterator of attribute names that can be overridden by a 
	 * sub type mapping.
	 */
	Iterable<String> getOverridableAttributeNames();

	/**
	 * Return an Iterator of all attribute names that can be overridden in this
	 * type mapping.
	 */
	Iterable<String> getAllOverridableAttributeNames();
	
	/**
	 * Return the column of the overridable attribute mapping (or attribute
	 * override) with the specified attribute name.
	 * <p>
	 * In JPA 2.0 this name can use dot-notation to designate nested attributes
	 * in embedded attribute mapping's embeddable type mapping.
	 */
	SpecifiedColumn resolveOverriddenColumn(String attributeName);


	// ********** association overrides **********

	/**
	 * Return an Iterator of associations names that can be overridden in this
	 * type mapping.
	 */
	Iterable<String> getOverridableAssociationNames();
	
	/**
	 * Return an Iterator of all associations names that can be overridden in this
	 * type mapping.
	 */
	Iterable<String> getAllOverridableAssociationNames();

	SpecifiedRelationship resolveOverriddenRelationship(String attributeName);


	// ********** validation **********

	/**
	 * Return whether any database metadata specific validation should occur.
	 * (For instance, if the connection is not active, then it should not.)
	 */
	boolean validatesAgainstDatabase();
}
