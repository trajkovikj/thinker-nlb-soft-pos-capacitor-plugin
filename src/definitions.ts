export interface ThinkerNlbSoftPosPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
