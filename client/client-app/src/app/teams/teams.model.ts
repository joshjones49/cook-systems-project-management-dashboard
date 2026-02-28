import { Company } from "../services/company.service";
import { User } from "../services/user.service";

export class Team {

    constructor(
        public id: number,
        public name: string,
        public description: string,
        public company: Company,
        public users: User[],
        public projects: any[]
    ) {}
}