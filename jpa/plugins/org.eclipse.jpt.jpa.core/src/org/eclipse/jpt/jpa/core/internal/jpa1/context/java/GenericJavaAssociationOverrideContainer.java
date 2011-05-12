/*******************************************************************************
 * Copyright (c) 2010, 2011 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.jpa1.context.java;

import org.eclipse.jpt.jpa.core.context.AssociationOverride;
import org.eclipse.jpt.jpa.core.context.JoinColumn;
import org.eclipse.jpt.jpa.core.context.Relationship;
import org.eclipse.jpt.jpa.core.context.RelationshipMapping;
import org.eclipse.jpt.jpa.core.context.Table;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaAssociationOverrideContainer;
import org.eclipse.jpt.jpa.core.context.java.JavaJpaContextNode;
import org.eclipse.jpt.jpa.core.context.java.JavaReadOnlyAssociationOverride;
import org.eclipse.jpt.jpa.core.context.java.JavaVirtualAssociationOverride;
import org.eclipse.jpt.jpa.core.internal.context.JoinColumnTextRangeResolver;
import org.eclipse.jpt.jpa.core.internal.context.JptValidator;
import org.eclipse.jpt.jpa.core.internal.context.MappingTools;
import org.eclipse.jpt.jpa.core.internal.context.TableTextRangeResolver;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverrideAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.AssociationOverridesAnnotation;
import org.eclipse.jpt.jpa.core.resource.java.NestableAssociationOverrideAnnotation;

/**
 * Java attribute override container
 */
public class GenericJavaAssociationOverrideContainer
	extends AbstractJavaOverrideContainer<
			JavaAssociationOverrideContainer.Owner,
			JavaReadOnlyAssociationOverride,
			JavaAssociationOverride,
			JavaVirtualAssociationOverride,
			NestableAssociationOverrideAnnotation
		>
	implements JavaAssociationOverrideContainer
{
	public GenericJavaAssociationOverrideContainer(JavaJpaContextNode parent, JavaAssociationOverrideContainer.Owner owner) {
		super(parent, owner);
	}


	public RelationshipMapping getRelationshipMapping(String attributeName) {
		return MappingTools.getRelationshipMapping(attributeName, this.owner.getOverridableTypeMapping());
	}

	public Relationship resolveOverriddenRelationship(String associationOverrideName) {
		return this.owner.resolveOverriddenRelationship(associationOverrideName);
	}

	public JptValidator buildJoinTableJoinColumnValidator(AssociationOverride override, JoinColumn column, org.eclipse.jpt.jpa.core.context.JoinColumn.Owner o, JoinColumnTextRangeResolver textRangeResolver) {
		return this.owner.buildJoinTableJoinColumnValidator(override, column, o, textRangeResolver);
	}

	public JptValidator buildJoinTableInverseJoinColumnValidator(AssociationOverride override, JoinColumn column, org.eclipse.jpt.jpa.core.context.JoinColumn.Owner o, JoinColumnTextRangeResolver textRangeResolver) {
		return this.owner.buildJoinTableInverseJoinColumnValidator(override, column, o, textRangeResolver);
	}

	public JptValidator buildTableValidator(AssociationOverride override, Table table, TableTextRangeResolver textRangeResolver) {
		return this.owner.buildTableValidator(override, table, textRangeResolver);
	}

	@Override
	protected String getOverrideAnnotationName() {
		return AssociationOverrideAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected String getOverrideContainerAnnotationName() {
		return AssociationOverridesAnnotation.ANNOTATION_NAME;
	}

	@Override
	protected JavaAssociationOverride buildSpecifiedOverride(NestableAssociationOverrideAnnotation overrideAnnotation) {
		return this.getJpaFactory().buildJavaAssociationOverride(this, overrideAnnotation);
	}

	@Override
	protected void initializeSpecifiedOverride(JavaAssociationOverride specifiedOverride, JavaVirtualAssociationOverride virtualOverride) {
		specifiedOverride.initializeFromVirtual(virtualOverride);
	}

	@Override
	protected JavaVirtualAssociationOverride buildVirtualOverride(String name) {
		return this.getJpaFactory().buildJavaVirtualAssociationOverride(this, name);
	}
}
