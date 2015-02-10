package ru.srms.larp.platform.sec

import org.apache.commons.lang.builder.HashCodeBuilder

class SpringUserSpringRole implements Serializable {

	private static final long serialVersionUID = 1

	SpringUser springUser
	SpringRole springRole

	boolean equals(other) {
		if (!(other instanceof SpringUserSpringRole)) {
			return false
		}

		other.springUser?.id == springUser?.id &&
		other.springRole?.id == springRole?.id
	}

	int hashCode() {
		def builder = new HashCodeBuilder()
		if (springUser) builder.append(springUser.id)
		if (springRole) builder.append(springRole.id)
		builder.toHashCode()
	}

	static SpringUserSpringRole get(long springUserId, long springRoleId) {
		SpringUserSpringRole.where {
			springUser == SpringUser.load(springUserId) &&
			springRole == SpringRole.load(springRoleId)
		}.get()
	}

	static boolean exists(long springUserId, long springRoleId) {
		SpringUserSpringRole.where {
			springUser == SpringUser.load(springUserId) &&
			springRole == SpringRole.load(springRoleId)
		}.count() > 0
	}

	static SpringUserSpringRole create(SpringUser springUser, SpringRole springRole, boolean flush = false) {
		def instance = new SpringUserSpringRole(springUser: springUser, springRole: springRole)
		instance.save(flush: flush, insert: true)
		instance
	}

	static boolean remove(SpringUser u, SpringRole r, boolean flush = false) {
		if (u == null || r == null) return false

		int rowCount = SpringUserSpringRole.where {
			springUser == SpringUser.load(u.id) &&
			springRole == SpringRole.load(r.id)
		}.deleteAll()

		if (flush) { SpringUserSpringRole.withSession { it.flush() } }

		rowCount > 0
	}

	static void removeAll(SpringUser u, boolean flush = false) {
		if (u == null) return

		SpringUserSpringRole.where {
			springUser == SpringUser.load(u.id)
		}.deleteAll()

		if (flush) { SpringUserSpringRole.withSession { it.flush() } }
	}

	static void removeAll(SpringRole r, boolean flush = false) {
		if (r == null) return

		SpringUserSpringRole.where {
			springRole == SpringRole.load(r.id)
		}.deleteAll()

		if (flush) { SpringUserSpringRole.withSession { it.flush() } }
	}

	static constraints = {
		springRole validator: { SpringRole r, SpringUserSpringRole ur ->
			if (ur.springUser == null) return
			boolean existing = false
			SpringUserSpringRole.withNewSession {
				existing = SpringUserSpringRole.exists(ur.springUser.id, r.id)
			}
			if (existing) {
				return 'userRole.exists'
			}
		}
	}

	static mapping = {
		id composite: ['springRole', 'springUser']
		version false
	}
}
