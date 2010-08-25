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
import org.eclipse.jpt.core.internal.context.orm.AbstractOrmMappedSuperclass;
import org.eclipse.jpt.core.jpa2.context.Cacheable2_0;
import org.eclipse.jpt.core.jpa2.context.CacheableHolder2_0;
import org.eclipse.jpt.core.resource.orm.v2_0.XmlCacheable_2_0;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkChangeTracking;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.context.EclipseLinkReadOnly;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.java.JavaEclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.context.orm.EclipseLinkConverterHolder;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkCaching;
import org.eclipse.jpt.eclipselink.core.context.orm.OrmEclipseLinkMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.internal.context.java.JavaEclipseLinkCustomizer;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkMappedSuperclassPrimaryKeyValidator;
import org.eclipse.jpt.eclipselink.core.internal.v1_1.context.EclipseLinkMappedSuperclassValidator;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCacheHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlChangeTrackingHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlConvertersHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlCustomizerHolder;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlMappedSuperclass;
import org.eclipse.jpt.eclipselink.core.resource.orm.XmlReadOnly;
import org.eclipse.jpt.utility.internal.iterables.CompositeIterable;
import org.eclipse.text.edits.ReplaceEdit;
import org.eclipse.wst.validation.internal.provisional.core.IMessage;
import org.eclipse.wst.validation.internal.provisional.core.IReporter;

public class OrmEclipseLinkMappedSuperclassImpl
	extends AbstractOrmMappedSuperclass
	implements OrmEclipseLinkMappedSuperclass, CacheableHolder2_0
{
	protected final OrmEclipseLinkReadOnly readOnly;
	
	protected final OrmEclipseLinkCustomizer customizer;
	
	protected final OrmEclipseLinkChangeTracking changeTracking;
	
	protected final OrmEclipseLinkCaching caching;
	
	protected final OrmEclipseLinkConverterHolder converterHolder;
	
	
	public OrmEclipseLinkMappedSuperclassImpl(OrmPersistentType parent, XmlMappedSuperclass resourceMapping) {
		super(parent, resourceMapping);
		this.readOnly = new OrmEclipseLinkReadOnly(this, (XmlReadOnly) this.resourceTypeMapping, getJavaReadOnly());
		this.customizer = new OrmEclipseLinkCustomizer(this, (XmlCustomizerHolder) this.resourceTypeMapping, getJavaCustomizer());
		this.changeTracking = new OrmEclipseLinkChangeTracking(this, (XmlChangeTrackingHolder) this.resourceTypeMapping, getJavaChangeTracking());
		this.caching = new OrmEclipseLinkCachingImpl(this, (XmlCacheHolder) this.resourceTypeMapping, (XmlCacheable_2_0) this.resourceTypeMapping, getJavaCaching());
		this.converterHolder = new OrmEclipseLinkConverterHolder(this, (XmlConvertersHolder) this.resourceTypeMapping);
	}
	
	
	@Override
	public XmlMappedSuperclass getResourceTypeMapping() {
		return (XmlMappedSuperclass) super.getResourceTypeMapping();
	}
	
	public boolean usesPrimaryKeyColumns() {
		return getResourceTypeMapping().getPrimaryKey() != null 
				|| usesJavaPrimaryKeyColumns();
	}
	
	public OrmEclipseLinkCaching getCaching() {
		return this.caching;
	}

	public EclipseLinkCustomizer getCustomizer() {
		return this.customizer;
	}

	public EclipseLinkChangeTracking getChangeTracking() {
		return this.changeTracking;
	}

	public OrmEclipseLinkReadOnly getReadOnly() {
		return this.readOnly;
	}
	
	public EclipseLinkConverterHolder getConverterHolder() {
		return this.converterHolder;
	}
		
	public Cacheable2_0 getCacheable() {
		return ((CacheableHolder2_0) getCaching()).getCacheable();
	}
	
	public boolean calculateDefaultCacheable() {
		return ((CacheableHolder2_0) getCaching()).calculateDefaultCacheable();
	}
	
	// **************** resource-context interaction ***************************
	
	@Override
	public void update() {
		super.update();
		this.readOnly.update(getJavaReadOnly());
		this.customizer.update(getJavaCustomizer());
		this.changeTracking.update(getJavaChangeTracking());
		this.caching.update(getJavaCaching());
		this.converterHolder.update(); 
	}
	
	@Override
	protected JavaEclipseLinkMappedSuperclass getJavaMappedSuperclassForDefaults() {
		return (JavaEclipseLinkMappedSuperclass) super.getJavaMappedSuperclassForDefaults();
	}
	
	protected EclipseLinkReadOnly getJavaReadOnly() {
		JavaEclipseLinkMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getReadOnly();
	}
	
	protected JavaEclipseLinkCustomizer getJavaCustomizer() {
		JavaEclipseLinkMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : (JavaEclipseLinkCustomizer) javaMappedSuperclass.getCustomizer();
	}
	
	protected EclipseLinkChangeTracking getJavaChangeTracking() {
		JavaEclipseLinkMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getChangeTracking();
	}
	
	protected JavaEclipseLinkCaching getJavaCaching() {
		JavaEclipseLinkMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? null : javaMappedSuperclass.getCaching();
	}
	
	protected boolean usesJavaPrimaryKeyColumns() {
		JavaEclipseLinkMappedSuperclass javaMappedSuperclass = getJavaMappedSuperclassForDefaults();
		return (javaMappedSuperclass == null) ? false : javaMappedSuperclass.usesPrimaryKeyColumns();
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


	// *********** validation ************
	
	@Override
	public void validate(List<IMessage> messages, IReporter reporter) {
		super.validate(messages, reporter);
		this.readOnly.validate(messages, reporter);
		this.customizer.validate(messages, reporter);
		this.changeTracking.validate(messages, reporter);
		this.caching.validate(messages, reporter);
	}
	
	@Override
	protected JptValidator buildPrimaryKeyValidator() {
		return new EclipseLinkMappedSuperclassPrimaryKeyValidator(this, buildTextRangeResolver());
	}

	@Override
	protected JptValidator buildTypeMappingValidator() {
		return new EclipseLinkMappedSuperclassValidator(this, getJavaResourcePersistentType(), buildTextRangeResolver());
	}
}
