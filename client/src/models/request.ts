export interface Request {
  id: number;
  description: string;
  address: string;
  createdAt: string;
  assignedAt: string | null;
  estimatedArrival: string | null;
  status: Status; 
  customerId: number;
  customerName: string;
  technicianId: number | null;
  technicianName: string | null;
}
export enum Status {
    PENDING ='PENDING',
    ASSIGNED = 'ASSIGNED',
    COMPLETED = 'COMPLETED',
    CANCELLED = 'CANCELLED'
}

