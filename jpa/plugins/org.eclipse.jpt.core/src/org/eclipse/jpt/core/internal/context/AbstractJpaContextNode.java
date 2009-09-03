/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.context;

import org.eclipse.jpt.core.JpaNode;
import org.eclipse.jpt.core.context.JpaContextNode;
import org.eclipse.jpt.core.context.MappingFileRoot;
import org.eclipse.jpt.core.context.persistence.PersistenceUnit;
import org.eclipse.jpt.core.internal.AbstractJpaNode;
import org.eclipse.jpt.db.Catalog;
import org.eclipse.jpt.db.Schema;
import org.eclipse.jpt.db.SchemaContainer;

public abstract class AbstractJpaContextNode
	extends AbstractJpaNode
	implements JpaContextNode
{

	// ********** constructor **********

	protected AbstractJpaContextNode(JpaNode parent) {
		super(parent);
	}


	// ********** JpaNode implentation **********

	/**
	 * covariant override
	 */
	@Override
	public JpaContextNode getParent() {
		return (JpaContextNode) super.getParent();
	}


	// ********** JpaContextNode implementation **********

	/**
	 * Overridden in GenericPersistence and GenericPersistenceXml to throw
	 * UnsupportedOperationException.
	 * Overridden in GenericRootContextNode to return null.
	 * Overridden in GenericPersistenceUnit to return itself.
	 */
	public PersistenceUnit getPersistenceUnit() {
		return this.getParent().getPersistenceUnit();
	}

	public MappingFileRoot getMappingFileRoot() {
		return this.getParent().getMappingFileRoot();
	}

	public Schema getContextDefaultDbSchema() {
		SchemaContainer dbSchemaContainer = this.getContextDefaultDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getContextDefaultSchema());
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em> catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getContextDefaultDbSchemaContainer() {
		String catalog = this.getContextDefaultCatalog();
		return (catalog != null) ? this.getDbCatalog(catalog) : this.getDatabase();
	}

	protected String getContextDefaultSchema() {
		MappingFileRoot mfr = this.getMappingFileRoot();
		return (mfr != null) ? mfr.getSchema() : this.getPersistenceUnit().getDefaultSchema();
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a <em>default</em>
	 * catalog), then the database probably does not support catalogs.
	 */
	public Catalog getContextDefaultDbCatalog() {
		String catalog = this.getContextDefaultCatalog();
		return (catalog == null) ? null : this.getDbCatalog(catalog);
	}

	protected String getContextDefaultCatalog() {
		MappingFileRoot mfr = this.getMappingFileRoot();
		return (mfr != null) ? mfr.getCatalog() : this.getPersistenceUnit().getDefaultCatalog();
	}
	
	public void postUpdate() {
		// do nothing
	}
}
