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
import org.eclipse.jpt.core.context.orm.EntityMappings;
import org.eclipse.jpt.core.context.orm.OrmPersistentType;
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

	/**
	 * Overridden in GenericRootContextNode to return null.
	 * Overridden in GenericEntityMappings to return itself.
	 */
	public EntityMappings getEntityMappings() {
		return this.getParent().getEntityMappings();
	}

	/**
	 * Overridden in GenericRootContextNode to return null.
	 * Overridden in GenericOrmPersistentType to return itself.
	 */
	public OrmPersistentType getOrmPersistentType() {
		return this.getParent().getOrmPersistentType();
	}

	public Schema getContextDefaultDbSchema() {
		SchemaContainer dbSchemaContainer = this.getContextDefaultDbSchemaContainer();
		return (dbSchemaContainer == null) ? null : dbSchemaContainer.getSchemaForIdentifier(this.getContextDefaultSchema());
	}

	/**
	 * If we don't have a catalog (i.e. we don't even have a *default* catalog),
	 * then the database probably does not support catalogs; and we need to
	 * get the schema directly from the database.
	 */
	public SchemaContainer getContextDefaultDbSchemaContainer() {
		String catalog = this.getContextDefaultCatalog();
		return (catalog != null) ? this.getDbCatalog(catalog) : this.getDatabase();
	}

	protected String getContextDefaultSchema() {
		EntityMappings em = this.getEntityMappings();
		return (em != null) ? em.getSchema() : this.getPersistenceUnit().getDefaultSchema();
	}

	public Catalog getContextDefaultDbCatalog() {
		String catalog = this.getContextDefaultCatalog();
		if (catalog == null) {
			return null;  // not even a default catalog (i.e. database probably does not support catalogs)
		}
		return this.getDbCatalog(catalog);
	}

	protected String getContextDefaultCatalog() {
		EntityMappings em = this.getEntityMappings();
		return (em != null) ? em.getCatalog() : this.getPersistenceUnit().getDefaultCatalog();
	}

}
