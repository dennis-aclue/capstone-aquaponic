import React from "react"
import {Link} from "react-router-dom"

export type UserType = {
	id: string
	shortDescription: string
	projectName: string
}
type UsersType = Array<UserType>

const Projects = () => {
	const [projects, setProjects] = React.useState<UsersType>([])

	React.useEffect(() => {
		//fetch users from json placeholder
		fetch("/api/projects")
			.then((response) => response.json())
			.then((json) => setProjects(json))
	}, [])

	return (
		<div className="projects">
			<h1>All users</h1>
{/*			<Link to="/users/new" state={{from: "all users", userName: "Bikashweb"}}>
				Add a new user
			</Link>*/}
			<div className="project__list">
				{projects &&
					projects.map((projects) => (
						//single user card
						<div className="project__card" key={projects.projectName}>
							<Link to={`/projects/${projects.id}`}>
								<p>
									Project name:
									<span className="normal">{projects.projectName}</span>
								</p>
							</Link>
						</div>
					))}
			</div>
		</div>
	)
}

export default Projects
