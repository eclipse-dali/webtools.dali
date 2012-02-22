/*******************************************************************************
 * Copyright (c) 2012 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 and Eclipse Distribution License v. 1.0
 * which accompanies this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *     Oracle - initial API and implementation
 *
 ******************************************************************************/
package org.eclipse.jpt.jpa.core.jpql.spi;

import org.eclipse.jpt.jpa.core.context.AttributeMapping;
import org.eclipse.jpt.jpa.core.context.Embeddable;
import org.eclipse.jpt.jpa.core.context.Entity;
import org.eclipse.jpt.jpa.core.context.MappedSuperclass;
import org.eclipse.persistence.jpa.jpql.spi.IEmbeddable;
import org.eclipse.persistence.jpa.jpql.spi.IEntity;
import org.eclipse.persistence.jpa.jpql.spi.IMappedSuperclass;
import org.eclipse.persistence.jpa.jpql.spi.IMappingBuilder;

/**
 * @version 3.2
 * @since 3.2
 * @author Pascal Filion
 */
public interface IManagedTypeBuilder {

	/**
	 * Creates
	 *
	 * @param provider
	 * @param mappedClass
	 * @param mappingBuilder
	 * @return
	 */
	IEmbeddable buildEmbeddable(JpaManagedTypeProvider provider,
	                            Embeddable mappedClass,
	                            IMappingBuilder<AttributeMapping> mappingBuilder);

	/**
	 * Creates
	 *
	 * @param provider
	 * @param mappedClass
	 * @param mappingBuilder
	 * @return
	 */
	IEntity buildEntity(JpaManagedTypeProvider provider,
	                    Entity mappedClass,
	                    IMappingBuilder<AttributeMapping> mappingBuilder);

	/**
	 * Creates
	 *
	 * @param provider
	 * @param mappedClass
	 * @param mappingBuilder
	 * @return
	 */
	IMappedSuperclass buildMappedSuperclass(JpaManagedTypeProvider provider,
	                                        MappedSuperclass mappedClass,
	                                        IMappingBuilder<AttributeMapping> mappingBuilder);
}