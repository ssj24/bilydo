import {UserData} from './UserData'
export interface TradeRules {
  borrowableEnd?: string,
  borrowableSrt?: string,
  category?: string,
  id?: number,
  imagesPath?: Array<string>,
  location?: string,
  priceDay?: number,
  priceMonth?: number,
  priceWeek?: number,
  producerId?: number,
  productName?: string,
  registDate?: string,
  state?: string,
  subscript?: string,
  usedTime?: string,
  arr?: UserData[]
}