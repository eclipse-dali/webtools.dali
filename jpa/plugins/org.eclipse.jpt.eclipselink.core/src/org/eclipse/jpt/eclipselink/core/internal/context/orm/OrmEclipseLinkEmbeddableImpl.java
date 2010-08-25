/*******************************************************************************
 * Copyright (c) 2008, 2010 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.context.orm;

import java.util.List;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
import org.eclipse.jpt.core.internal.context.JptValidator;
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkEmbeddable;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkEmbeddable;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkTypeMappingValidator;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlEmbeddable;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkEmbeddableImpl
	extends AbstractOrmEmbeddable
	implements OrmEclipseLinkEmbeddable
{
	protected final OrmEclipseLinkCustomizer customizer;
	
	protected final OrmEclipseLinkChangeTracking changeTracking;
	
	protected final OrmEclipseLinkConverterHolder converterHolder;
	
	
	public OrmEclipseLinkEmbeddableImpl(OrmPersistentType parent, XmlEmbeddable resourceMapping) {
		super(parent, resourceMapping);
		this.customizer = new OrmEclipseLinkCustomizer(this, (XmlCustomizerHolder) this.resourceTypeMapping, getJavaCustomizer());
		this.changeTracking = new OrmEclipseLinkChangeTracking(this, (XmlChangeTrackingHolder) this.resourceTypeMapping, getJavaChangeTracking());
		this.converterHolder = new OrmEclipseLinkConverterHolder(this, (XmlConvertersHolder) this.resourceTypeMapping); 
	}
	
	
	@Override
	public XmlEmbeddable getResourceTypeMapping() {
		return (XmlEmbeddable) super.getResourceTypeMapping();
	}
	
	public boolean usesPrimaryKeyColumns() {
		return false;
	}
	
	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}
	
	public EclipseLinkChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	public EclipseLinkConverterHolder getConverterHolder() {
		return this.converterHolder;
	}

	
	
	// **************** resource-context interaction ***************************

	@Override
	public void update() {
		super.update();
		this.customizer.update(getJavaCustomizer());
		this.changeTracking.update(getJavaChangeTracking());
		this.converterHolder.update(); 
	}
	
	@Override
	protected JavaEclipseLinkEmbeddable getJavaEmbeddableForDefaults() {
		return (JavaEclipseLinkEmbeddable) super.getJavaEmbeddableForDefaults();
	}
	
	
	protected JavaEclipseLinkCustomizer getJavaCustomizer() {
		JavaEclipseLinkEmbeddable javaEmbeddable = getJavaEmbeddableForDefaults();
		return (javaEmbeddable == null) ? null : (JavaEclipseLinkCustomizer) javaEmbeddable.getCustomizer();
	}
	
	protected EclipseLinkChangeTracking getJavaChangeTracking() {
		JavaEclipseLinkEmbeddable javaEmbeddable = getJavaEmbeddableForDefaults();
		return (javaEmbeddable == null) ? null : javaEmbeddable.getChangeTracking();
	}


	//************************* refactoring ************************

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenameTypeEdits(IType originalType, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenameTypeEdits(originalType, newName),
			this.createCustomizerRenameTypeEdits(originalType, newName),
			this.createConverterHolderRenameTypeEdits(originalType, newName));
	}

	protected Iterable<ReplaceEdit> createCustomizerRenameTypeEdits(IType originalType, String newName) {
		return this.customizer.createRenameTypeEdits(originalType, newName);
	}

	protected Iterable<ReplaceEdit> createConverterHolderRenameTypeEdits(IType originalType, String newName) {
		return this.converterHolder.createRenameTypeEdits(originalType, newName);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return new CompositeIterable<ReplaceEdit>(
			super.createMoveTypeEdits(originalType, newPackage),
			this.createCustomizerMoveTypeEdits(originalType, newPackage),
			this.createConverterHolderMoveTypeEdits(originalType, newPackage));
	}

	protected Iterable<ReplaceEdit> createCustomizerMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.customizer.createMoveTypeEdits(originalType, newPackage);
	}

	protected Iterable<ReplaceEdit> createConverterHolderMoveTypeEdits(IType originalType, IPackageFragment newPackage) {
		return this.converterHolder.createMoveTypeEdits(originalType, newPackage);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Iterable<ReplaceEdit> createRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return new CompositeIterable<ReplaceEdit>(
			super.createRenamePackageEdits(originalPackage, newName),
			this.createCustomizerRenamePackageEdits(originalPackage, newName),
			this.createConverterHolderRenamePackageEdits(originalPackage, newName));
	}

	protected Iterable<ReplaceEdit> createCustomizerRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.customizer.createRenamePackageEdits(originalPackage, newName);
	}

	protected Iterable<ReplaceEdit> createConverterHolderRenamePackageEdits(IPackageFragment originalPackage, String newName) {
		return this.converterHolder.createRenamePackageEdits(originalPackage, newName);
	}


	// **************** validation **************************************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.customizer.validate(messages, reporter);
		this.changeTracking.validate(messages, reporter);
	}

	@Override
	protected JptValidator buildTypeMappingValidator() {
		return new EclipseLinkTypeMappingValidator(this, getJavaResourcePersistentType(), buildTextRangeResolver());
	}
}
