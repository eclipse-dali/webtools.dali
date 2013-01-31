/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import org.eclipse.jpt.common.utility.internal.iterable.IterableTools;
import org.eclipse.jpt.common.utility.internal.iterable.SubIterableWrapper;
import org.eclipse.jpt.common.utility.transformer.Transformer;
import org.eclipse.jpt.jpa.core.MappingKeys;
import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.ReadOnlyTable;
import org.eclipse.jpt.jpa.core.context.TypeMapping;
import org.eclipse.jpt.jpa.core.jpa2.context.DerivedIdentity2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.MapsIdDerivedIdentityStrategy2_0;
import org.eclipse.jpt.jpa.core.jpa2.context.SingleRelationshipMapping2_0;

/**
 * Gather some of the behavior common to the Java and XML models. :-(
 */
public class TypeMappingTools {

	// ********** single relationship mappings **********

	/**
	 * Return whether the specified attribute is a derived ID for the specified
	 * type mapping.
	 */
	public static boolean attributeIsDerivedId(TypeMapping typeMapping, String attributeName) {
		if (attributeName == null) {
			return false;
		}
		// the attribute name may be qualified - we test only the first attribute name
		int dotIndex = attributeName.indexOf('.');
		attributeName = (dotIndex == -1) ? attributeName : attributeName.substring(0, dotIndex);
		return IterableTools.contains(getMapsIdDerivedIdAttributeNames(typeMapping), attributeName);
	}

	/**
	 * Return the names of all the ID attributes derived from
	 * single-relationship mappings for the specified type mapping
	 * (i.e. the names of the ID attributes that are specified by a single-
	 * relationship mapping's "maps ID" setting).
	 */
	protected static Iterable<String> getMapsIdDerivedIdAttributeNames(TypeMapping typeMapping) {
		return IterableTools.transform(getMapsIdDerivedIdentityStrategies(typeMapping), MapsIdDerivedIdentityStrategy2_0.ID_ATTRIBUTE_NAME_TRANSFORMER);
	}

	/**
	 * Return all the single-relationship "maps ID" derived identity strategies
	 * for the specified type mapping.
	 */
	protected static Iterable<MapsIdDerivedIdentityStrategy2_0> getMapsIdDerivedIdentityStrategies(TypeMapping typeMapping) {
		return IterableTools.transform(getMapsIdDerivedIdentities(typeMapping), DerivedIdentity2_0.MAPS_ID_DERIVED_IDENTITY_STRATEGY_TRANSFORMER);
	}

	/**
	 * Return all the single-relationship "maps ID" derived identities for the
	 * specified type mapping.
	 */
	protected static Iterable<DerivedIdentity2_0> getMapsIdDerivedIdentities(TypeMapping typeMapping) {
		return IterableTools.filter(getDerivedIdentities(typeMapping), DerivedIdentity2_0.USES_MAPS_ID_DERIVED_IDENTITY_STRATEGY);
	}

	/**
	 * Return all the single-relationship derived identities for the specified
	 * type mapping.
	 */
	protected static Iterable<DerivedIdentity2_0> getDerivedIdentities(TypeMapping typeMapping) {
		return IterableTools.transform(getSingleRelationshipMappings(typeMapping), SingleRelationshipMapping2_0.DERIVED_IDENTITY_TRANSFORMER);
	}

	/**
	 * Return all the single-relationship attribute mappings for the specified
	 * type mapping.
	 */
	protected static Iterable<SingleRelationshipMapping2_0> getSingleRelationshipMappings(TypeMapping typeMapping) {
		return new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(getSingleRelationshipMappings_(typeMapping));
	}

	@SuppressWarnings("unchecked")
	protected static Iterable<AttributeMapping> getSingleRelationshipMappings_(TypeMapping typeMapping) {
		return IterableTools.concatenate(
					typeMapping.getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
					typeMapping.getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)
				);
	}


	// ********** attribute mappings transformer **********

	public static final Transformer<TypeMapping, Iterable<AttributeMapping>> ATTRIBUTE_MAPPINGS_TRANSFORMER = new AttributeMappingsTransformer();
	static class AttributeMappingsTransformer
		implements Transformer<TypeMapping, Iterable<AttributeMapping>>
	{
		public Iterable<AttributeMapping> transform(TypeMapping mapping) {
			return mapping.getAttributeMappings();
		}
	}


	// ********** associated tables transformer **********

	public static final Transformer<TypeMapping, Iterable<ReadOnlyTable>> ASSOCIATED_TABLES_TRANSFORMER = new AssociatedTablesTransformer();
	static class AssociatedTablesTransformer
		implements Transformer<TypeMapping, Iterable<ReadOnlyTable>>
	{
		public Iterable<ReadOnlyTable> transform(TypeMapping mapping) {
			return mapping.getAssociatedTables();
		}
	}


	// ********** overridable attribute names transformer **********

	public static final Transformer<TypeMapping, Iterable<String>> OVERRIDABLE_ATTRIBUTE_NAMES_TRANSFORMER = new OverridableAttributeNamesTransformer();
	static class OverridableAttributeNamesTransformer
		implements Transformer<TypeMapping, Iterable<String>>
	{
		public Iterable<String> transform(TypeMapping mapping) {
			return mapping.getOverridableAttributeNames();
		}
	}


	// ********** overridable association names transformer **********

	public static final Transformer<TypeMapping, Iterable<String>> OVERRIDABLE_ASSOCIATION_NAMES_TRANSFORMER = new OverridableAssociationNamesTransformer();
	static class OverridableAssociationNamesTransformer
		implements Transformer<TypeMapping, Iterable<String>>
	{
		public Iterable<String> transform(TypeMapping mapping) {
			return mapping.getOverridableAssociationNames();
		}
	}


	// ********** constructor **********

	/**
	 * Suppress default constructor, ensuring non-instantiability.
	 */
	private TypeMappingTools() {
		super();
		throw new UnsupportedOperationException();
	}
}
