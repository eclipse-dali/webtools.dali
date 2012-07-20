/*******************************************************************************
 * Copyright (c) 2007, 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.internal.context;

import org.eclipse.jpt.common.core.JptResourceType;
import org.eclipse.jpt.jpa.core.JpaNode;
import org.eclipse.jpt.jpa.core.context.JpaContextNode;
import org.eclipse.jpt.jpa.core.context.MappingFile;
import org.eclipse.jpt.jpa.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.jpa.core.internal.AbstractJpaNode;
import org.eclipse.jpt.jpa.db.Catalog;
import org.eclipse.jpt.jpa.db.Schema;
import org.eclipse.jpt.jpa.db.SchemaContainer;

public abstract class AbstractJpaContextNode
	extends AbstractJpaNode
	implements JpaContextNode
{
	protected AbstractJpaContextNode(JpaNode parent) {
		super(parent);
	}


	// ********** synchronize/update **********

	public void synchronizeWithResourceModel() {
		// NOP
	}

	/**
	 * convenience method
	 */
	protected void synchronizeNodesWithResourceModel(Iterable<? extends JpaContextNode> nodes) {
		for (JpaContextNode node : nodes) {
			node.synchronizeWithResourceModel();
		}
	}

	public void update() {
		// NOP
	}

	/**
	 * convenience method
	 */
	protected void updateNodes(Iterable<? extends JpaContextNode> nodes) {
		for (JpaContextNode node : nodes) {
			node.update();
		}
	}


	// ********** containment hierarchy **********

	/**
	 * covariant override
	 */
	@Override
	public JpaContextNode getParent() {
		return (JpaContextNode) super.getParent();
	}

	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.java.AbstractJavaJpaContextNode#getResourceType() AbstractJavaJpaContextNode}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.java.GenericJarFile#getResourceType() GenericJarFile}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.orm.GenericOrmXml#getResourceType() GenericOrmXml}
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.persistence.GenericPersistenceXml#getResourceType() GenericPersistenceXml}
	 * </ul>
	 */
	public JptResourceType getResourceType() {
		return this.getParent().getResourceType();
	}
	
	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.persistence.AbstractPersistenceUnit#getPersistenceUnit() AbstractPersistenceUnit}
	 * to return itself
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericRootContextNode#getPersistenceUnit() GenericRootContextNode}
	 * to return <code>null</code>
	 * </ul>
	 */
	public PersistenceUnit getPersistenceUnit() {
		return this.getParent().getPersistenceUnit();
	}

	/**
	 * Overridden in:<ul>
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.context.orm.AbstractEntityMappings#getMappingFileRoot() AbstractEntityMappings}
	 * to return itself
	 * <li>{@link org.eclipse.jpt.jpa.core.internal.jpa1.context.GenericRootContextNode#getMappingFileRoot() GenericRootContextNode}
	 * to return <code>null</code>
	 * </ul>
	 */
	public MappingFile.Root getMappingFileRoot() {
		return this.getParent().getMappingFileRoot();
	}


	// ********** database stuff **********

	public Schema getContextDefaultDbSchema() {
		SchemaContainer dbSchemaContainer = this.getContextDefaultDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getContextDefaultSchema());
	}

	protected String getContextDefaultSchema() {
		MappingFile.Root mfr = this.getMappingFileRoot();
		return (mfr != null) ? mfr.getSchema() : this.getPersistenceUnit().getDefaultSchema();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getContextDefaultDbSchemaContainer() {
		String catalog = this.getContextDefaultCatalog();
		return (catalog != null) ? this.resolveDbCatalog(catalog) : this.getDatabase();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getContextDefaultDbCatalog() {
		String catalog = this.getContextDefaultCatalog();
		return (catalog == null) ? null : this.resolveDbCatalog(catalog);
	}

	protected String getContextDefaultCatalog() {
		MappingFile.Root mfr = this.getMappingFileRoot();
		return (mfr != null) ? mfr.getCatalog() : this.getPersistenceUnit().getDefaultCatalog();
	}
	
}
