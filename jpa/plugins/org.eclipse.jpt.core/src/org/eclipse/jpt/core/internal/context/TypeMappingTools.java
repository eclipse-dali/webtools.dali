/*******************************************************************************
 * Copyright (c) 2006, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context;

import java.util.Iterator;
import org.eclipse.jpt.core.MappingKeys;
import org.eclipse.jpt.core.context.AttributeMapping;
import org.eclipse.jpt.core.context.ReadOnlyTable;
import org.eclipse.jpt.core.context.TypeMapping;
import org.eclipse.jpt.core.jpa2.context.SingleRelationshipMapping2_0;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.jpt.utility.internal.iterables.FilteringIterable;
import org.eclipse.jpt.utility.internal.iterables.SubIterableWrapper;
import org.eclipse.jpt.utility.internal.iterables.TransformationIterable;

/**
 * Gather some of the behavior common to the Java and XML models. :-(
 */
public class TypeMappingTools
{
	// ********** single relationship mappings **********

	public static Iterable<String> getMappedByRelationshipAttributeNames(TypeMapping typeMapping) {
		return new TransformationIterable<SingleRelationshipMapping2_0, String>(getMapsIdRelationshipMappings(typeMapping)) {
				@Override
				protected String transform(SingleRelationshipMapping2_0 attributeMapping) {
					return attributeMapping.getDerivedIdentity().getMapsIdDerivedIdentityStrategy().getValue();
				}
			};
	}

	public static Iterable<SingleRelationshipMapping2_0> getMapsIdRelationshipMappings(TypeMapping typeMapping) {
		return new FilteringIterable<SingleRelationshipMapping2_0>(getSingleRelationshipMappings(typeMapping)) {
				@Override
				protected boolean accept(SingleRelationshipMapping2_0 attributeMapping) {
					return attributeMapping.getDerivedIdentity().usesMapsIdDerivedIdentityStrategy();
				}
			};
	}

	protected static Iterable<SingleRelationshipMapping2_0> getSingleRelationshipMappings(TypeMapping typeMapping) {
		return new SubIterableWrapper<AttributeMapping, SingleRelationshipMapping2_0>(getSingleRelationshipMappings_(typeMapping));
	}

	@SuppressWarnings("unchecked")
	protected static Iterable<AttributeMapping> getSingleRelationshipMappings_(TypeMapping typeMapping) {
		return new CompositeIterable<AttributeMapping>(
					typeMapping.getAllAttributeMappings(MappingKeys.ONE_TO_ONE_ATTRIBUTE_MAPPING_KEY),
					typeMapping.getAllAttributeMappings(MappingKeys.MANY_TO_ONE_ATTRIBUTE_MAPPING_KEY)
				);
	}


	// ********** attribute mappings transformer **********

	public static final Transformer<TypeMapping, Iterator<AttributeMapping>> ATTRIBUTE_MAPPINGS_TRANSFORMER = new AttributeMappingsTransformer();
	static class AttributeMappingsTransformer
		implements Transformer<TypeMapping, Iterator<AttributeMapping>>
	{
		public Iterator<AttributeMapping> transform(TypeMapping mapping) {
			return mapping.attributeMappings();
		}
	}


	// ********** associated tables transformer **********

	public static final Transformer<TypeMapping, Iterator<ReadOnlyTable>> ASSOCIATED_TABLES_TRANSFORMER = new AssociatedTablesTransformer();
	static class AssociatedTablesTransformer
		implements Transformer<TypeMapping, Iterator<ReadOnlyTable>>
	{
		public Iterator<ReadOnlyTable> transform(TypeMapping mapping) {
			return mapping.associatedTables();
		}
	}


	// ********** overridable attribute names transformer **********

	public static final Transformer<TypeMapping, Iterator<String>> OVERRIDABLE_ATTRIBUTE_NAMES_TRANSFORMER = new OverridableAttributeNamesTransformer();
	static class OverridableAttributeNamesTransformer
		implements Transformer<TypeMapping, Iterator<String>>
	{
		public Iterator<String> transform(TypeMapping mapping) {
			return mapping.overridableAttributeNames();
		}
	}


	// ********** overridable association names transformer **********

	public static final Transformer<TypeMapping, Iterator<String>> OVERRIDABLE_ASSOCIATION_NAMES_TRANSFORMER = new OverridableAssociationNamesTransformer();
	static class OverridableAssociationNamesTransformer
		implements Transformer<TypeMapping, Iterator<String>>
	{
		public Iterator<String> transform(TypeMapping mapping) {
			return mapping.overridableAssociationNames();
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
