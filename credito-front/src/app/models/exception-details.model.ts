export interface IExceptionDetails {
  title: string;
  code: number;
  message: string;
  details?: string;
  timestamp?: string;
  path?: string;
  cause?: string;
}
