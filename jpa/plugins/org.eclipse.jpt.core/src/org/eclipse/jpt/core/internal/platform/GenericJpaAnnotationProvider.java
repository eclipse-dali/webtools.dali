/*******************************************************************************
 * Copyright (c) 2007, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.platform;

import java.util.List;
import org.eclipse.jpt.core.JpaAnnotationProvider;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverrideImpl.AssociationOverrideAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AssociationOverridesImpl.AssociationOverridesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverrideImpl.AttributeOverrideAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.AttributeOverridesImpl.AttributeOverridesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.BasicImpl.BasicAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ColumnImpl.ColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorColumnImpl.DiscriminatorColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.DiscriminatorValueImpl.DiscriminatorValueAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddableImpl.EmbeddableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedIdImpl.EmbeddedIdAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EmbeddedImpl.EmbeddedAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EntityImpl.EntityAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.EnumeratedImpl.EnumeratedAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.GeneratedValueImpl.GeneratedValueAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.IdClassImpl.IdClassAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.IdImpl.IdAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.InheritanceImpl.InheritanceAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinColumnImpl.JoinColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinColumnsImpl.JoinColumnsAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.JoinTableImpl.JoinTableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.LobImpl.LobAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ManyToManyImpl.ManyToManyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.ManyToOneImpl.ManyToOneAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.MapKeyImpl.MapKeyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.MappedSuperclassImpl.MappedSuperclassAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueriesImpl.NamedNativeQueriesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedNativeQueryImpl.NamedNativeQueryAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedQueriesImpl.NamedQueriesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.NamedQueryImpl.NamedQueryAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OneToManyImpl.OneToManyAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OneToOneImpl.OneToOneAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.OrderByImpl.OrderByAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumnImpl.PrimaryKeyJoinColumnAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.PrimaryKeyJoinColumnsImpl.PrimaryKeyJoinColumnsAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTableImpl.SecondaryTableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SecondaryTablesImpl.SecondaryTablesAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.SequenceGeneratorImpl.SequenceGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableGeneratorImpl.TableGeneratorAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TableImpl.TableAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TemporalImpl.TemporalAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.TransientImpl.TransientAnnotationDefinition;
import org.eclipse.jpt.core.internal.resource.java.VersionImpl.VersionAnnotationDefinition;
import org.eclipse.jpt.core.resource.java.AnnotationDefinition;

/**
 * 
 */
public class GenericJpaAnnotationProvider extends AbstractJpaAnnotationProvider
{	
	// singleton
	private static final JpaAnnotationProvider INSTANCE = new GenericJpaAnnotationProvider();

	/**
	 * Return the singleton.
	 */
	public static JpaAnnotationProvider instance() {
		return INSTANCE;
	}

	/**
	 * Ensure single instance.
	 */
	private GenericJpaAnnotationProvider() {
		super();
	}
	
	@Override
	protected void addDelegateAnnotationProvidersTo(List<JpaAnnotationProvider> providers) {
		//none
	}
	
	@Override
	protected void addTypeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(EmbeddableAnnotationDefinition.instance());
		definitions.add(EntityAnnotationDefinition.instance());
		definitions.add(MappedSuperclassAnnotationDefinition.instance());
	}

	@Override
	protected void addTypeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(AssociationOverrideAnnotationDefinition.instance());
		definitions.add(AssociationOverridesAnnotationDefinition.instance());
		definitions.add(AttributeOverrideAnnotationDefinition.instance());
		definitions.add(AttributeOverrideAnnotationDefinition.instance());
		definitions.add(AttributeOverridesAnnotationDefinition.instance());
		definitions.add(DiscriminatorColumnAnnotationDefinition.instance());
		definitions.add(DiscriminatorValueAnnotationDefinition.instance());
		definitions.add(IdClassAnnotationDefinition.instance());
		definitions.add(InheritanceAnnotationDefinition.instance());
		definitions.add(NamedQueryAnnotationDefinition.instance());
		definitions.add(NamedQueriesAnnotationDefinition.instance());
		definitions.add(NamedNativeQueryAnnotationDefinition.instance());
		definitions.add(NamedNativeQueriesAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnsAnnotationDefinition.instance());
		definitions.add(SecondaryTableAnnotationDefinition.instance());
		definitions.add(SecondaryTablesAnnotationDefinition.instance());
		definitions.add(SequenceGeneratorAnnotationDefinition.instance());
		definitions.add(TableAnnotationDefinition.instance());
		definitions.add(TableGeneratorAnnotationDefinition.instance());
	}
	
	@Override
	protected void addAttributeMappingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(BasicAnnotationDefinition.instance());
		definitions.add(EmbeddedAnnotationDefinition.instance());
		definitions.add(EmbeddedIdAnnotationDefinition.instance());
		definitions.add(IdAnnotationDefinition.instance());
		definitions.add(ManyToManyAnnotationDefinition.instance());
		definitions.add(ManyToOneAnnotationDefinition.instance());
		definitions.add(OneToManyAnnotationDefinition.instance());
		definitions.add(OneToOneAnnotationDefinition.instance());
		definitions.add(TransientAnnotationDefinition.instance());
		definitions.add(VersionAnnotationDefinition.instance());
	}
	
	@Override
	protected void addAttributeSupportingAnnotationDefinitionsTo(List<AnnotationDefinition> definitions) {
		definitions.add(AssociationOverrideAnnotationDefinition.instance());
		definitions.add(AssociationOverridesAnnotationDefinition.instance());
		definitions.add(AttributeOverrideAnnotationDefinition.instance());
		definitions.add(AttributeOverridesAnnotationDefinition.instance());
		definitions.add(ColumnAnnotationDefinition.instance());
		definitions.add(EnumeratedAnnotationDefinition.instance());
		definitions.add(GeneratedValueAnnotationDefinition.instance());
		definitions.add(JoinColumnAnnotationDefinition.instance());
		definitions.add(JoinColumnsAnnotationDefinition.instance());
		definitions.add(JoinTableAnnotationDefinition.instance());
		definitions.add(LobAnnotationDefinition.instance());
		definitions.add(MapKeyAnnotationDefinition.instance());
		definitions.add(OrderByAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnAnnotationDefinition.instance());
		definitions.add(PrimaryKeyJoinColumnsAnnotationDefinition.instance());
		definitions.add(SequenceGeneratorAnnotationDefinition.instance());
		definitions.add(TableGeneratorAnnotationDefinition.instance());
		definitions.add(TemporalAnnotationDefinition.instance());
	}
}
