/*
 * Copyright 2012 Steve Chaloner
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package models.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import play.db.ebean.Model;
import be.objectify.deadbolt.core.models.Role;

/**
 * @author Steve Chaloner (steve@objectify.be)
 */
@Entity
@Table(name="role")
public class SecurityRole extends Model implements Role {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	public Long id;

	public String name;

	public static final Model.Finder<Long, SecurityRole> find = new Model.Finder<Long, SecurityRole>(
			Long.class, SecurityRole.class);

	public String getName() {
		return name;
	}

	public static SecurityRole findByName(String name) {
		return find.where().eq("name", name).findUnique();
	}
}
