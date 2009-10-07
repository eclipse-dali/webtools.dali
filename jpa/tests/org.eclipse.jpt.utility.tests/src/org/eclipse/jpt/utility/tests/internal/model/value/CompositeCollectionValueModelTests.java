/*******************************************************************************
 * Copyright (c) 2007, 2008 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.utility.tests.internal.model.value;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import junit.framework.TestCase;
import org.eclipse.jpt.utility.internal.Bag;
import org.eclipse.jpt.utility.internal.CollectionTools;
import org.eclipse.jpt.utility.internal.Transformer;
import org.eclipse.jpt.utility.internal.iterators.CompositeIterator;
import org.eclipse.jpt.utility.internal.iterators.TransformationIterator;
import org.eclipse.jpt.utility.internal.model.AbstractModel;
import org.eclipse.jpt.utility.internal.model.value.CollectionAspectAdapter;
import org.eclipse.jpt.utility.internal.model.value.CompositeCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimpleCollectionValueModel;
import org.eclipse.jpt.utility.internal.model.value.SimplePropertyValueModel;
import org.eclipse.jpt.utility.model.value.CollectionValueModel;
import org.eclipse.jpt.utility.model.value.PropertyValueModel;
import org.eclipse.jpt.utility.model.value.WritablePropertyValueModel;
import org.eclipse.jpt.utility.tests.internal.TestTools;

@SuppressWarnings("nls")
public class CompositeCollectionValueModelTests extends TestCase {
	private Neighborhood neighborhood;
	private WritablePropertyValueModel<Neighborhood> neighborhoodHolder;

	public CompositeCollectionValueModelTests(String name) {
		super(name);
	}

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.neighborhood = new Neighborhood("Hanna-Barbera");
		this.neighborhoodHolder = new SimplePropertyValueModel<Neighborhood>(this.neighborhood);
	}

	@Override
	protected void tearDown() throws Exception {
		TestTools.clear(this);
		super.tearDown();
	}

	public void testSynch1() {
		this.verifySynch(this.buildAllMembersComposite(this.neighborhoodHolder));
	}

	public void testSynch2() {
		this.verifySynch(this.buildAllMembersComposite2(this.neighborhoodHolder));
	}

	private void verifySynch(CollectionValueModel<Member> compositeCVM) {
		assertEquals(0, CollectionTools.size(compositeCVM.iterator()));
		Bag<Family> familiesSynch = new CoordinatedBag<Family>(this.buildFamiliesAspectAdapter(this.neighborhoodHolder));
		Bag<Member> membersSynch = new CoordinatedBag<Member>(compositeCVM);
		this.populateNeighborhood(this.neighborhood);

		Family jetsons = this.neighborhood.familyNamed("Jetson");

		assertEquals(3, familiesSynch.size());
		assertEquals(12, CollectionTools.size(this.neighborhood.allMembers()));
		assertEquals(12, membersSynch.size());
		assertEquals(CollectionTools.bag(this.neighborhood.allMembers()), membersSynch);
		assertEquals(membersSynch, CollectionTools.bag(compositeCVM.iterator()));

		jetsons.removeMember(jetsons.memberNamed("Astro"));
		assertEquals(3, familiesSynch.size());
		assertEquals(11, CollectionTools.size(this.neighborhood.allMembers()));
		assertEquals(11, membersSynch.size());
		assertEquals(CollectionTools.bag(this.neighborhood.allMembers()), membersSynch);
		assertEquals(membersSynch, CollectionTools.bag(compositeCVM.iterator()));

		jetsons.removeMember(jetsons.memberNamed("Judy"));
		assertEquals(3, familiesSynch.size());
		assertEquals(10, CollectionTools.size(this.neighborhood.allMembers()));
		assertEquals(10, membersSynch.size());
		assertEquals(CollectionTools.bag(this.neighborhood.allMembers()), membersSynch);
		assertEquals(membersSynch, CollectionTools.bag(compositeCVM.iterator()));

		jetsons.addMember("Fido");
		assertEquals(3, familiesSynch.size());
		assertEquals(11, CollectionTools.size(this.neighborhood.allMembers()));
		assertEquals(11, membersSynch.size());
		assertEquals(CollectionTools.bag(this.neighborhood.allMembers()), membersSynch);
		assertEquals(membersSynch, CollectionTools.bag(compositeCVM.iterator()));

		this.neighborhood.removeFamily(jetsons);
		assertEquals(2, familiesSynch.size());
		assertEquals(7, CollectionTools.size(this.neighborhood.allMembers()));
		assertEquals(7, membersSynch.size());
		assertEquals(CollectionTools.bag(this.neighborhood.allMembers()), membersSynch);
		assertEquals(membersSynch, CollectionTools.bag(compositeCVM.iterator()));

		Family bears = this.neighborhood.addFamily("Bear");
			bears.addMember("Yogi");
		assertEquals(3, familiesSynch.size());
		assertEquals(8, CollectionTools.size(this.neighborhood.allMembers()));
		assertEquals(8, membersSynch.size());
		assertEquals(CollectionTools.bag(this.neighborhood.allMembers()), membersSynch);
		assertEquals(membersSynch, CollectionTools.bag(compositeCVM.iterator()));

		bears.addMember("Boo-Boo");
		assertEquals(3, familiesSynch.size());
		assertEquals(9, CollectionTools.size(this.neighborhood.allMembers()));
		assertEquals(9, membersSynch.size());
		assertEquals(CollectionTools.bag(this.neighborhood.allMembers()), membersSynch);
		assertEquals(membersSynch, CollectionTools.bag(compositeCVM.iterator()));

		Neighborhood n2 = new Neighborhood("Hanna-Barbera 2");
		this.neighborhoodHolder.setValue(n2);
		this.populateNeighborhood(n2);
		assertEquals(3, familiesSynch.size());
		assertEquals(12, CollectionTools.size(n2.allMembers()));
		assertEquals(12, membersSynch.size());
		assertEquals(CollectionTools.bag(n2.allMembers()), membersSynch);
		assertEquals(membersSynch, CollectionTools.bag(compositeCVM.iterator()));
	}

	public void testNoTransformer() {
		SimpleCollectionValueModel<String> subCVM1 = new SimpleCollectionValueModel<String>();
		SimpleCollectionValueModel<String> subCVM2 = new SimpleCollectionValueModel<String>();
		Collection<CollectionValueModel<String>> collection = new ArrayList<CollectionValueModel<String>>();
		collection.add(subCVM1);
		collection.add(subCVM2);
		Bag<String> synchBag = new CoordinatedBag<String>(new CompositeCollectionValueModel<CollectionValueModel<String>, String>(collection));

		assertEquals(0, synchBag.size());

		subCVM1.add("foo");
		subCVM1.add("bar");
		subCVM1.add("baz");
		assertEquals(3, synchBag.size());
		assertTrue(synchBag.contains("foo"));

		subCVM2.add("joo");
		subCVM2.add("jar");
		subCVM2.add("jaz");
		assertEquals(6, synchBag.size());
		assertTrue(synchBag.contains("foo"));
		assertTrue(synchBag.contains("jaz"));

		subCVM1.remove("baz");
		assertEquals(5, synchBag.size());
		assertFalse(synchBag.contains("baz"));
	}

	public void testDuplicateItem() {
		Bag<Member> synchBag = new CoordinatedBag<Member>(this.buildAllMembersComposite(this.neighborhoodHolder));
		this.populateNeighborhood(this.neighborhood);
		boolean exCaught = false;
		try {
			this.neighborhood.addFamily(this.neighborhood.familyNamed("Jetson"));
		} catch (IllegalStateException ex) {
			if (ex.getMessage().indexOf("duplicate component") != -1) {
				exCaught = true;
			}
		}
		assertTrue(exCaught);
		assertEquals(12, synchBag.size());
	}

	public void testHasListeners() {
		CompositeCollectionValueModel<Family, Member> compositeCVM = this.buildAllMembersComposite(this.neighborhoodHolder);
		CoordinatedBag<Member> synchBag = new CoordinatedBag<Member>(compositeCVM);
		this.populateNeighborhood(this.neighborhood);
		Family jetsons = this.neighborhood.familyNamed("Jetson");

		assertTrue(compositeCVM.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		assertTrue(jetsons.hasAnyCollectionChangeListeners(Family.MEMBERS_COLLECTION));

		compositeCVM.removeCollectionChangeListener(CollectionValueModel.VALUES, synchBag);
		assertFalse(compositeCVM.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		assertFalse(jetsons.hasAnyCollectionChangeListeners(Family.MEMBERS_COLLECTION));

		compositeCVM.addCollectionChangeListener(CollectionValueModel.VALUES, synchBag);
		assertTrue(compositeCVM.hasAnyCollectionChangeListeners(CollectionValueModel.VALUES));
		assertTrue(jetsons.hasAnyCollectionChangeListeners(Family.MEMBERS_COLLECTION));
	}

	private void populateNeighborhood(Neighborhood n) {
		Family family1 = n.addFamily("Flintstone");
			family1.addMember("Fred");
			family1.addMember("Wilma");
			family1.addMember("Pebbles");
			family1.addMember("Dino");
		Family family2 = n.addFamily("Rubble");
			family2.addMember("Barney");
			family2.addMember("Betty");
			family2.addMember("Bamm-Bamm");
		Family family3 = n.addFamily("Jetson");
			family3.addMember("George");
			family3.addMember("Jane");
			family3.addMember("Judy");
			family3.addMember("Elroy");
			family3.addMember("Astro");
	}

	private CollectionValueModel<Family> buildFamiliesAspectAdapter(PropertyValueModel<Neighborhood> communeHolder) {
		return new CollectionAspectAdapter<Neighborhood, Family>(communeHolder, Neighborhood.FAMILIES_COLLECTION) {
			@Override
			protected Iterator<Family> iterator_() {
				return this.subject.families();
			}
		};
	}

	CollectionValueModel<Member> buildMembersAdapter(Family family) {
		return new CollectionAspectAdapter<Family, Member>(Family.MEMBERS_COLLECTION, family) {
			@Override
			protected Iterator<Member> iterator_() {
				return this.subject.members();
			}
		};
	}

	private CompositeCollectionValueModel<Family, Member> buildAllMembersComposite(PropertyValueModel<Neighborhood> communeHolder) {
		// override #transform(Object)
		return new CompositeCollectionValueModel<Family, Member>(this.buildFamiliesAspectAdapter(communeHolder)) {
			@Override
			protected CollectionValueModel<Member> transform(Family family) {
				return CompositeCollectionValueModelTests.this.buildMembersAdapter(family);
			}
		};
	}

	private CollectionValueModel<Member> buildAllMembersComposite2(PropertyValueModel<Neighborhood> communeHolder) {
		// build a custom Transformer
		return new CompositeCollectionValueModel<Family, Member>(this.buildFamiliesAspectAdapter(communeHolder), this.buildTransformer());
	}

	private Transformer<Family, CollectionValueModel<Member>> buildTransformer() {
		return new Transformer<Family, CollectionValueModel<Member>>() {
			public CollectionValueModel<Member> transform(Family family) {
				return CompositeCollectionValueModelTests.this.buildMembersAdapter(family);
			}
			@Override
			public String toString() {
				return "Local Transformer";
			}
		};
	}


// ********** inner classes **********

	/**
	 * inner class
	 */
	public class Neighborhood extends AbstractModel {
		private String name;
			public static final String NAME_PROPERTY = "name";
		private Collection<Family> families = new ArrayList<Family>();
			public static final String FAMILIES_COLLECTION = "families";
	
		public Neighborhood(String name) {
			super();
			this.name = name;
		}
	
		public String getName() {
			return this.name;
		}
		
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}
	
		public Iterator<Family> families() {
			return this.families.iterator();
		}
	
		public Family addFamily(String familyName) {
			return this.addFamily(new Family(familyName));
		}

		// backdoor to allow duplicates
		public Family addFamily(Family family) {
			this.addItemToCollection(family, this.families, FAMILIES_COLLECTION);
			return family;
		}

		public void removeFamily(Family family) {
			this.removeItemFromCollection(family, this.families, FAMILIES_COLLECTION);
		}
	
		public Family familyNamed(String familyName) {
			for (Family family : this.families) {
				if (family.getName().equals(familyName)) {
					return family;
				}
			}
			throw new IllegalArgumentException(familyName);
		}
	
		public Iterator<Member> allMembers() {
			return new CompositeIterator<Member>(this.membersIterators());
		}
	
		private Iterator<Iterator<Member>> membersIterators() {
			return new TransformationIterator<Family, Iterator<Member>>(this.families()) {
				@Override
				protected Iterator<Member> transform(Family family) {
					return family.members();
				}
			};
		}
	
		public Member memberNamed(String familyName, String memberName) {
			return this.familyNamed(familyName).memberNamed(memberName);
		}
	
		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.name);
		}

	}


	/**
	 * inner class
	 */
	public class Family extends AbstractModel {
		private String name;
			public static final String NAME_PROPERTY = "name";
		private Collection<Member> members = new ArrayList<Member>();
			public static final String MEMBERS_COLLECTION = "members";
	
		public Family(String name) {
			super();
			this.name = name;
		}
	
		public String getName() {
			return this.name;
		}
		
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}
	
		public Iterator<Member> members() {
			return this.members.iterator();
		}
	
		public Member addMember(String memberName) {
			Member member = new Member(memberName);
			this.addItemToCollection(member, this.members, MEMBERS_COLLECTION);
			return member;
		}
		
		public void removeMember(Member member) {
			this.removeItemFromCollection(member, this.members, MEMBERS_COLLECTION);
		}
	
		public Member memberNamed(String memberName) {
			for (Member member : this.members) {
				if (member.getName().equals(memberName)) {
					return member;
				}
			}
			throw new IllegalArgumentException(memberName);
		}
	
		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.name);
		}
	
	}


	/**
	 * inner class
	 */
	public class Member extends AbstractModel {
		private String name;
			public static final String NAME_PROPERTY = "name";

		public Member(String name) {
			super();
			this.name = name;
		}

		public String getName() {
			return this.name;
		}
	
		public void setName(String name) {
			Object old = this.name;
			this.name = name;
			this.firePropertyChanged(NAME_PROPERTY, old, name);
		}

		@Override
		public void toString(StringBuilder sb) {
			sb.append(this.name);
		}

	}

}
