export interface Review {
  id: string,
  user: User,
  title: string,
  star: number,
  content: string,
  createdAt: string,
  location: Location,
  tags: Tag[]
}

export interface User {
  id: string,
  name: string
}

export interface Location {
  id: string,
  name: string
}

export interface Tag {
  id: string,
  name: string
}
