import { WebPlugin } from '@capacitor/core';

import type { ThinkerNlbSoftPosPlugin } from './definitions';

export class ThinkerNlbSoftPosWeb extends WebPlugin implements ThinkerNlbSoftPosPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
